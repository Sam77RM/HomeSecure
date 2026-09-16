/* Almacén compartido de cámaras: sincroniza Configuración <-> Cámaras usando localStorage */
(function (global) {

    const STORAGE_KEY = 'hs_camaras';

    const VIDEO_POOL = [
        'https://www.pexels.com/download/video/7437293/',
        'https://www.pexels.com/download/video/6995054/',
        'https://www.pexels.com/download/video/5823707/',
        'https://www.pexels.com/download/video/7578543/'
    ];

    const CAMARAS_DEFAULT = [
        { id: 'cam-1', nombre: 'Cámara Frontal', ubicacion: 'Exterior', ip: '192.168.1.10', tipo: 'Exterior', video: VIDEO_POOL[0], activa: true },
        { id: 'cam-2', nombre: 'Cámara Entrada', ubicacion: 'Exterior', ip: '192.168.1.11', tipo: 'Exterior', video: VIDEO_POOL[1], activa: true },
        { id: 'cam-3', nombre: 'Cámara Sala', ubicacion: 'Interior', ip: '192.168.1.12', tipo: 'Interior', video: VIDEO_POOL[2], activa: true },
        { id: 'cam-4', nombre: 'Cámara Trasera', ubicacion: 'Exterior', ip: '192.168.1.13', tipo: 'Exterior', video: VIDEO_POOL[3], activa: true }
    ];

    function obtenerCamaras() {
        try {
            const raw = localStorage.getItem(STORAGE_KEY);
            if (raw) {
                const parsed = JSON.parse(raw);
                if (Array.isArray(parsed) && parsed.length) return parsed;
            }
        } catch (e) {}
        guardarCamaras(CAMARAS_DEFAULT);
        return CAMARAS_DEFAULT.slice();
    }

    function guardarCamaras(lista) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(lista));
    }

    function agregarCamara(datos) {
        const lista = obtenerCamaras();
        const video = VIDEO_POOL[lista.length % VIDEO_POOL.length];
        const nueva = Object.assign({ id: 'cam-' + Date.now(), video, activa: true }, datos);
        lista.push(nueva);
        guardarCamaras(lista);
        return lista;
    }

    function actualizarCamara(id, cambios) {
        const lista = obtenerCamaras();
        const idx = lista.findIndex((c) => c.id === id);
        if (idx !== -1) {
            lista[idx] = Object.assign({}, lista[idx], cambios);
            guardarCamaras(lista);
        }
        return lista;
    }

    function eliminarCamara(id) {
        const lista = obtenerCamaras().filter((c) => c.id !== id);
        guardarCamaras(lista);
        return lista;
    }

    global.CamarasStore = {
        obtenerCamaras,
        guardarCamaras,
        agregarCamara,
        actualizarCamara,
        eliminarCamara,
        VIDEO_POOL
    };

})(window);

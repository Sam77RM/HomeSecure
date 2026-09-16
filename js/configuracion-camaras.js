/* Panel Configuración > Cámaras: lista, agrega, edita y elimina cámaras compartiendo estado con camaras.html */
(function () {

    const lista = document.getElementById('lista-camaras');
    const form = document.getElementById('form-camara');
    if (!lista || !form || !window.CamarasStore) return;

    const store = window.CamarasStore;

    const inputEditandoId = document.getElementById('cam-editando-id');
    const inputNombre = document.getElementById('cam-nombre');
    const inputUbicacion = document.getElementById('cam-ubicacion');
    const inputIp = document.getElementById('cam-ip');
    const selectTipo = document.getElementById('cam-tipo');
    const btnGuardar = document.getElementById('btn-guardar-camara');
    const btnAgregar = document.getElementById('btn-agregar-camara');
    const btnCancelar = document.getElementById('btn-cancelar-camara');

    function render() {
        const camaras = store.obtenerCamaras();

        lista.innerHTML = camaras.map((cam) => {
            const activa = cam.activa !== false;
            return `
            <div class="config-item" data-id="${cam.id}">
                <div class="config-item-icon purple"><i class="fa-solid fa-camera"></i></div>
                <div class="config-item-info">
                    <h4>${cam.nombre}</h4>
                    <p>${cam.tipo || 'Exterior'}${cam.ip ? ' · ' + cam.ip : ''}${cam.ubicacion ? ' · ' + cam.ubicacion : ''}</p>
                </div>
                <div class="config-item-status ${activa ? 'online' : 'offline'}">${activa ? 'En línea' : 'Apagada'}</div>
                <div class="config-item-actions">
                    <button type="button" class="icon-btn btn-editar-camara" title="Editar"><i class="fa-solid fa-pen"></i></button>
                    <button type="button" class="icon-btn danger btn-eliminar-camara" title="Eliminar"><i class="fa-solid fa-trash"></i></button>
                </div>
            </div>
        `;
        }).join('');
    }

    function limpiarFormulario() {
        inputEditandoId.value = '';
        inputNombre.value = '';
        inputUbicacion.value = '';
        inputIp.value = '';
        selectTipo.selectedIndex = 0;
        btnGuardar.textContent = 'Guardar cámara';
    }

    if (btnAgregar) {
        btnAgregar.addEventListener('click', () => {
            const abierto = form.classList.contains('open') && !inputEditandoId.value;
            limpiarFormulario();
            if (abierto) {
                form.classList.remove('open');
            } else {
                form.classList.add('open');
            }
        });
    }

    if (btnCancelar) {
        btnCancelar.addEventListener('click', () => {
            limpiarFormulario();
            form.classList.remove('open');
        });
    }

    lista.addEventListener('click', (e) => {
        const item = e.target.closest('.config-item');
        if (!item) return;
        const id = item.dataset.id;

        if (e.target.closest('.btn-editar-camara')) {
            const cam = store.obtenerCamaras().find((c) => c.id === id);
            if (!cam) return;
            inputEditandoId.value = cam.id;
            inputNombre.value = cam.nombre || '';
            inputUbicacion.value = cam.ubicacion || '';
            inputIp.value = cam.ip || '';
            selectTipo.value = cam.tipo || 'Interior';
            btnGuardar.textContent = 'Guardar cambios';
            form.classList.add('open');
            inputNombre.focus();
        }

        if (e.target.closest('.btn-eliminar-camara')) {
            store.eliminarCamara(id);
            render();
        }
    });

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const datos = {
            nombre: inputNombre.value.trim(),
            ubicacion: inputUbicacion.value.trim(),
            ip: inputIp.value.trim(),
            tipo: selectTipo.value
        };

        if (!datos.nombre) return;

        if (inputEditandoId.value) {
            store.actualizarCamara(inputEditandoId.value, datos);
        } else {
            store.agregarCamara(datos);
        }

        limpiarFormulario();
        form.classList.remove('open');
        render();
    });

    render();

})();

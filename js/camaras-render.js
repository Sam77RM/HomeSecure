/* Genera las tarjetas de cámaras en camaras.html a partir de CamarasStore (configurado en Configuración).
   Pagina automáticamente: si hay más cámaras de las que caben en una pantalla, aparecen más páginas. */
(function () {

    const grid = document.getElementById('camera-grid');
    const pagContainer = document.getElementById('camera-pagination');
    if (!grid || !window.CamarasStore) return;

    const store = window.CamarasStore;
    const CAMARAS_POR_PAGINA = 4;

    let paginaActual = 1;

    function renderTarjetas(camarasPagina) {
        grid.innerHTML = camarasPagina.map((cam) => {
            const activa = cam.activa !== false;
            return `
            <article class="camera-card" data-id="${cam.id}">
                <div class="camera-image ${activa ? '' : 'camara-apagada'}">
                    <video autoplay muted loop playsinline>
                        <source src="${cam.video}" type="video/mp4">
                    </video>
                    <div class="camera-number ${activa ? 'is-on' : 'is-off'}" role="button" tabindex="0"
                        title="${activa ? 'Cámara encendida (clic para apagar)' : 'Cámara apagada (clic para encender)'}">
                        <span class="status-dot"></span>
                    </div>
                    <div class="camera-info">
                        <h3>${cam.nombre}</h3>
                    </div>
                    <div class="camera-actions">
                        <button class="btn-expandir" title="Pantalla completa"><i class="fa-solid fa-expand"></i></button>
                        <button class="btn-grabar" title="Grabar"><i class="fa-solid fa-circle"></i></button>
                        <button class="btn-foto" title="Tomar foto"><i class="fa-solid fa-camera"></i></button>
                    </div>
                </div>
            </article>
        `;
        }).join('');

        grid.querySelectorAll('.camera-number').forEach((luz) => {
            luz.addEventListener('click', () => alternarEstado(luz));
            luz.addEventListener('keydown', (e) => {
                if (e.key === 'Enter' || e.key === ' ') {
                    e.preventDefault();
                    alternarEstado(luz);
                }
            });
        });
    }

    function alternarEstado(luz) {
        const card = luz.closest('.camera-card');
        const id = card && card.dataset.id;
        if (!id) return;

        const lista = store.obtenerCamaras();
        const cam = lista.find((c) => c.id === id);
        if (!cam) return;

        const nuevaActiva = !(cam.activa !== false);
        store.actualizarCamara(id, { activa: nuevaActiva });

        luz.classList.toggle('is-on', nuevaActiva);
        luz.classList.toggle('is-off', !nuevaActiva);
        luz.title = nuevaActiva ? 'Cámara encendida (clic para apagar)' : 'Cámara apagada (clic para encender)';

        const imagen = card.querySelector('.camera-image');
        if (imagen) imagen.classList.toggle('camara-apagada', !nuevaActiva);
    }

    function renderPaginacion(totalPaginas) {
        if (!pagContainer) return;

        let html = `<li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="prev">Previous</a>
        </li>`;

        for (let p = 1; p <= totalPaginas; p++) {
            html += `<li class="page-item ${p === paginaActual ? 'active' : ''}">
                <a class="page-link" href="#" data-page="${p}">${p}</a>
            </li>`;
        }

        html += `<li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="next">Next</a>
        </li>`;

        pagContainer.innerHTML = html;

        pagContainer.querySelectorAll('.page-link').forEach((link) => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const valor = link.dataset.page;

                if (valor === 'prev') {
                    if (paginaActual > 1) paginaActual--;
                } else if (valor === 'next') {
                    if (paginaActual < totalPaginas) paginaActual++;
                } else {
                    paginaActual = parseInt(valor, 10);
                }

                render();
            });
        });
    }

    function actualizarTextoContador(total) {
        const countText = document.getElementById('camera-count-text');
        if (!countText) return;
        countText.textContent = `${total === 1 ? 'La cámara está' : `Las ${total} cámaras están`} conectada${total === 1 ? '' : 's'} y transmitiendo correctamente.`;
    }

    function render() {
        const camaras = store.obtenerCamaras();
        const totalPaginas = Math.max(1, Math.ceil(camaras.length / CAMARAS_POR_PAGINA));

        if (paginaActual > totalPaginas) paginaActual = totalPaginas;
        if (paginaActual < 1) paginaActual = 1;

        const inicio = (paginaActual - 1) * CAMARAS_POR_PAGINA;
        const camarasPagina = camaras.slice(inicio, inicio + CAMARAS_POR_PAGINA);

        renderTarjetas(camarasPagina);
        renderPaginacion(totalPaginas);
        actualizarTextoContador(camaras.length);
    }

    render();

})();

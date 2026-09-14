const boton = document.getElementById('menuToggle');
const sidebar = document.querySelector('.sidebar');
const overlay = document.getElementById('sidebarOverlay');

function abrirSidebar() {
    sidebar.classList.add('activo');
    overlay.classList.add('activo');
}

function cerrarSidebar() {
    sidebar.classList.remove('activo');
    overlay.classList.remove('activo');
}

boton.addEventListener('click', () => {
    sidebar.classList.contains('activo') ? cerrarSidebar() : abrirSidebar();
});

// Cerrar al hacer clic fuera del sidebar (en el overlay)
overlay.addEventListener('click', cerrarSidebar);

// Cerrar al elegir una sección del menú
document.querySelectorAll('.sidebar nav a').forEach((link) => {
    link.addEventListener('click', cerrarSidebar);
});

// Cerrar con la tecla Escape
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') cerrarSidebar();
});

//PAra usuarios
const formUsuario = document.getElementById('form-usuario');
if (formUsuario) {
    formUsuario.addEventListener('submit', function(e){
        e.preventDefault();
        this.classList.remove('open');
    });
}

//Funciones para pag Historial
const ctx = document.getElementById('grafico-eventos');

if (ctx) {
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['11 Jun', '12 Jun', '13 Jun', '14 Jun', '15 Jun', '16 Jun', '17 Jun'],
        datasets: [
            {
                label: 'Alta',
                data: [1, 0, 2, 1, 0, 1, 1],
                backgroundColor: '#ff5656'
            },
            {
                label: 'Media',
                data: [2, 1, 3, 2, 1, 2, 2],
                backgroundColor: '#f7b034'
            },
            {
                label: 'Baja',
                data: [1, 2, 1, 0, 2, 1, 1],
                backgroundColor: '#d9b23f'
            },
            {
                label: 'Info',
                data: [2, 2, 3, 2, 2, 3, 2],
                backgroundColor: '#45d37d'
            }
        ]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                stacked: true,
                ticks: { color: '#8ea0b5' },
                grid: { color: 'rgba(255,255,255,.06)' }
            },
            y: {
                stacked: true,
                ticks: { color: '#8ea0b5' },
                grid: { color: 'rgba(255,255,255,.06)' }
            }
        },
        plugins: {
            legend: {
                labels: { color: '#d5deea' }
            }
        }
    }
});
}

/*script para configuracion*/
const tabs = document.querySelectorAll('.config-tab');
const panels = {
    camaras: document.getElementById('panel-camaras'),
    sensores: document.getElementById('panel-sensores'),
    general: document.getElementById('panel-general')
};

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        tabs.forEach(t => t.classList.remove('active'));
        tab.classList.add('active');

        Object.keys(panels).forEach(key => {
            panels[key].style.display = 'none';
        });

        panels[tab.dataset.tab].style.display = 'block';
    });
});

const formCamara = document.getElementById('form-camara');
if (formCamara) {
    formCamara.addEventListener('submit', function(e){
        e.preventDefault();
        this.classList.remove('open');
    });
}

const formSensor = document.getElementById('form-sensor');
if (formSensor) {
    formSensor.addEventListener('submit', function(e){
        e.preventDefault();
        this.classList.remove('open');
    });
}

//para automatizaciones
const formAutomatizacion = document.getElementById('form-automatizacion');
if (formAutomatizacion) {
    formAutomatizacion.addEventListener('submit', function(e){
        e.preventDefault();
    });
}

/* ================= CÁMARAS: agrandar, grabar y tomar foto ================= */
const cameraCards = document.querySelectorAll('.camera-card');

function actualizarBotonExpandir(card) {
    const btn = card.querySelector('.btn-expandir');
    if (!btn) return;
    const expandido = card.classList.contains('is-featured');
    btn.querySelector('i').className = expandido ? 'fa-solid fa-compress' : 'fa-solid fa-expand';
    btn.title = expandido ? 'Salir de pantalla completa' : 'Pantalla completa';
}

function capturarFoto(card) {
    const media = card.querySelector('.camera-image video, .camera-image img');
    if (!media) return;

    const canvas = document.createElement('canvas');
    canvas.width = media.videoWidth || media.naturalWidth || media.clientWidth;
    canvas.height = media.videoHeight || media.naturalHeight || media.clientHeight;

    try {
        canvas.getContext('2d').drawImage(media, 0, 0, canvas.width, canvas.height);

        const nombreCamara = card.querySelector('.camera-info h3')?.textContent.trim() || 'camara';
        const fecha = new Date().toISOString().slice(0, 19).replace(/[:T]/g, '-');

        const enlace = document.createElement('a');
        enlace.href = canvas.toDataURL('image/png');
        enlace.download = `${nombreCamara}-${fecha}.png`;
        enlace.click();

        const imagen = card.querySelector('.camera-image');
        const flash = document.createElement('div');
        flash.className = 'camera-flash';
        imagen.appendChild(flash);
        flash.addEventListener('animationend', () => flash.remove());
    } catch (error) {
        alert('No se pudo capturar la foto: el video de muestra viene de otro dominio (restricción CORS). Con la transmisión real de la cámara esta función funcionará sin problema.');
    }
}

cameraCards.forEach((card) => {
    const grid = card.closest('.camera-grid');
    const expandBtn = card.querySelector('.btn-expandir');
    const recordBtn = card.querySelector('.btn-grabar');
    const photoBtn = card.querySelector('.btn-foto');

    if (expandBtn && grid) {
        expandBtn.addEventListener('click', () => {
            const yaEsProtagonista = card.classList.contains('is-featured');

            cameraCards.forEach((c) => c.classList.remove('is-featured', 'is-minimized'));

            if (!yaEsProtagonista) {
                grid.classList.add('focused');
                card.classList.add('is-featured');
                cameraCards.forEach((c) => {
                    if (c !== card) c.classList.add('is-minimized');
                });
            } else {
                grid.classList.remove('focused');
            }

            cameraCards.forEach(actualizarBotonExpandir);
        });
    }

    if (recordBtn) {
        recordBtn.addEventListener('click', () => {
            const grabando = card.classList.toggle('is-recording');
            recordBtn.classList.toggle('is-active', grabando);
            recordBtn.title = grabando ? 'Detener grabación' : 'Grabar';
            recordBtn.querySelector('i').className = grabando ? 'fa-solid fa-stop' : 'fa-solid fa-circle';
        });
    }

    if (photoBtn) {
        photoBtn.addEventListener('click', () => capturarFoto(card));
    }
});

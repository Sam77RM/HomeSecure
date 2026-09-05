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
document.getElementById('form-usuario').addEventListener('submit', function(e){
    e.preventDefault();
    this.classList.remove('open');
});

//Funciones para pag Historial
const ctx = document.getElementById('grafico-eventos');

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

document.getElementById('form-camara').addEventListener('submit', function(e){
    e.preventDefault();
    this.classList.remove('open');
});

document.getElementById('form-sensor').addEventListener('submit', function(e){
    e.preventDefault();
    this.classList.remove('open');
});

//para automatizaciones
    document.getElementById('form-automatizacion').addEventListener('submit', function(e){
        e.preventDefault();
    });

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

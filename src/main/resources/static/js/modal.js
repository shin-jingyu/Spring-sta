const Modal = document.getElementById('Modal');
if (Modal) {
  Modal.addEventListener('show.bs.modal', event => {
    const button = event.relatedTarget;
    const title = button.getAttribute('data-bs-whatever');
    const modalTitle = Modal.querySelector('.modal-title');
    const modalcontent = Modal.querySelector('textarea');
    
    modalTitle.textContent = `New message to ${title}`;
    modalcontent.value = title;
  });
}

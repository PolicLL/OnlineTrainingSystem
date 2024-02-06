function showConfirmation(form) {
    document.getElementById('confirmation-dialog').style.display = 'block';

    window.deleteForm = form;

    document.addEventListener('click', clickOutsideDialog);

    return false;
}

function confirmDelete() {
    document.getElementById('confirmation-dialog').style.display = 'none';

    if (window.deleteForm) {
        window.deleteForm.submit();
    }
    document.removeEventListener('click', clickOutsideDialog);
}

function cancelDelete() {
    document.getElementById('confirmation-dialog').style.display = 'none';

    document.removeEventListener('click', clickOutsideDialog);
}

function clickOutsideDialog(event) {
    var confirmationDialog = document.getElementById('confirmation-dialog');

    if (!confirmationDialog.contains(event.target)) {
        document.getElementById('confirmation-dialog').style.display = 'none';


        document.removeEventListener('click', clickOutsideDialog);
    }
}

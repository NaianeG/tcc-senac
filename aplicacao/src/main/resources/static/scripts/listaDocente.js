
document.addEventListener("DOMContentLoaded", function () {
    var zerarBancoModal = document.getElementById("zerarBancoModal");
    var confirmZerarBancoBtn = document.getElementById("confirmZerarBancoBtn");

    function openZerarBancoModal(pessoaId) {
        confirmZerarBancoBtn.onclick = function() {
            window.location.href = "/zerarBancoHoras/" + pessoaId;
        };
        zerarBancoModal.style.display = "block";
    }

    function closeZerarBancoModal() {
        zerarBancoModal.style.display = "none";
    }

    window.openZerarBancoModal = openZerarBancoModal;
    window.closeZerarBancoModal = closeZerarBancoModal;

    window.addEventListener("click", function(event) {
        if (event.target == zerarBancoModal) {
            closeZerarBancoModal();
        }
    });
});

window.addEventListener('load', () => {
    const forms = document.getElementsByClassName('validation-form');
    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);

window.addEventListener('DOMContentLoaded', (event) => {
    fetch('http://localhost:8083/my-info.html')
        .then(response => {
            if (response.status === 301) {
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function submitForm() {
    const formData = {
        name: document.getElementById('name').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        phoneNumber: document.getElementById('phoneNumber').value
    };
    fetch('http://localhost:8083/profile', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (response.ok) {
                window.location.href = 'http://localhost:8083/profile';
            } else {
                alert("입력 정보가 형식에 알맞지 않습니다.")
                console.error('Update failed:', response);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

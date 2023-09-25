const { createApp } = Vue
const options = {
    data() {
        return {
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            showAlert: false,
            showForm: true,

        };
    },

    methods: {
        login() {
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(response => {
                    if (this.email == "admin@admin.com") {
                        location.href = '../../manager/manager.html';
                    } else {
                        location.href = '../pages/accounts.html';
                    }
                })
                .catch(error => {
                    console.log(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: error.response.data,
                        confirmButtonColor: '#5b31be93',
                    })
    })},
        mostrarLog(){
            this.showForm = false;
        },
        mostrarSingUp(){
            this.showForm = true;
        },

        logout() {
            axios.post('/api/logout')
                .then(response => {
                    location.href = '/web/pages/index.html';
                })
                .catch(error => console.error(error))
        },

        signUp(event) {
            console.log("HOLA JULI")
            event.preventDefault()
            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`, 
            {headers: {'content-type': 'application/x-www-form-urlencoded'}})
                .then(response => {
                 this.login()
            })
            .catch(error => {
                console.log(error);
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                    confirmButtonColor: '#5b31be93',
                })
})
        }

    }

}
const app = createApp(options)
    .mount('#app')




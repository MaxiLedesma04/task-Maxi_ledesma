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
                    console.error(error);
                    if (error.response && error.response.status === 401) {
                        this.showAlert = true;
                    }
                })
        },
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
        }

    }

}
const app = createApp(options)
    .mount('#app')




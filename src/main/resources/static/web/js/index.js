const { createApp } = Vue
const options = {
    data() {
        return {
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            showAlert: false,

        };
    },

    methods: {
        login() {
            axios.post('http://localhost:8080/api/login', `email=${this.email}&password=${this.password}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(response => {
                    if ("admin@admin.com".includes(this.email)) {
                        location.href = '/manager/manager.html';
                    } else {
                        location.href = '/web/pages/accounts.html';
                    }
                })
                .catch(error => {
                    console.error(error);
                    if (error.response && error.response.status === 401) {
                        this.showAlert = true;
                    }
                })
        },

        logout() {
            axios.post('http://localhost:8080/api/logout')
                .then(response => {
                    location.href = '/web/pages/index.html';
                })
                .catch(error => console.error(error))
        },

        signUp(event) {
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



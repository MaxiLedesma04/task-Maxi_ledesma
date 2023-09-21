const { createApp } = Vue
const url = "/api/clients/current"
const options = {
    data() {
        return {
            clients: [],
            clients_accounts: [],
            accounts: [],
            loans:[],
        };
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(url)
                .then(response => {
                    this.clients = response.data
                    console.log(this.clients)
                    this.clients_accounts = this.clients.accounts
                    const numberF = Intl.NumberFormat('es-US', {
                        style: 'currency',
                        currency: 'USD',
                        maximumSignificantDigits: 1
                    })
                    this.loans = this.clients.loans;

                    for (const accounts of this.clients_accounts) {
                        const aux = {
                            id: accounts.id,
                            number: accounts.number,
                            dateTime: accounts.dateTime,
                            balance: numberF.format(accounts.balance),
                        }
                        this.accounts.push(aux)
                    }

                })
                .catch(error => console.error(error))
        },
        logout() {
            axios.post('http://localhost:8080/api/logout')
                .then(response => {
                    location.href = '/web/pages/index.html';
                })
                .catch(error => console.error(error))
        },
        aliminarAcc(id){
            Swal.fire({title: '¿Quieres eliminar la cuenta?',
                inputAttributes: {autocapitalize: 'off'},
                showCancelButton: true, 
                confirmButtonText: "Seguro",
                showLoaderOnConfirm: true,
                 preConfirm: login =>{
                 axios.delete("/appi/clients/current/accounts/deactivate", `id=${id}`)
                    .then(response => {location.href = '../pages/accounts.html'})
                    .catch(error=> {
                        swal.fire({icon: 'error',
                    text: error.response.data,
                confirmButtonColor: '#5b31be93',});
                    });
                },
                allowOutsideClick: () => !Swal.isLoading(),
        })},

        createAcc(){
            Swal.fire({title: '¿Quieres crear una nueva cuenta?',
                inputAttributes: {autocapitalize: 'off'},
                showCancelButton: true, 
                confirmButtonText: "Seguro",
                showLoaderOnConfirm: true,
                 preConfirm: login =>{
                 axios.post('/api/clients/current/accounts')
                    .then(reponse => {location.href = '../pages/accounts.html'})
                    .catch(error=> {
                        swal.fire({icon: 'error',
                    text: error.response.data,
                confirmButtonColor: '#5b31be93',});
                    });
                },
                allowOutsideClick: () => !Swal.isLoading(),

            });
        }
    }

}
const app = createApp(options)
    .mount('#app')








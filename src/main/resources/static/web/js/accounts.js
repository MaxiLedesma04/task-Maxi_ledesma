const { createApp } = Vue
const url = "/api/clients/current"
const options = {
    data() {
        return {
            clients: [],
            clients_accounts: [],
            accounts: [],
            loans:[],
            type:"",
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
                    this.client_loans = this.clients.loans;
                    for (const loans of this.client_loans) {
                        const auxi = {
                            id: loans.id,
                            amount: numberF.format(loans.amount),
                            payments: loans.payments,
                            name: loans.name
                        }
                        this.loans.push(auxi)
                    }

                    for (const accounts of this.clients_accounts) {
                        const aux = {
                            id: accounts.id,
                            number: accounts.number,
                            dateTime: accounts.dateTime,
                            balance: numberF.format(accounts.balance),
                            active: accounts.active
                        }
                        this.accounts.push(aux)
                    }

                })
                .catch(error => console.error(error))
        },
        logout() {
            axios.post('/api/logout')
                .then(response => {
                    location.href = '/web/pages/index.html';
                })
                .catch(error => console.error(error))
        },
        aliminarAcc(number){
            Swal.fire({title: '¿Quieres eliminar la cuenta?',
                inputAttributes: {autocapitalize: 'off'},
                showCancelButton: true, 
                confirmButtonText: "Seguro",
                showLoaderOnConfirm: true,
                 preConfirm: login =>{
                 axios.patch("/api/clients/current/accounts/deactivate", `accNumber=${number}`)
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
            Swal.fire({
                title: '¿Esta seguro de crear una cuenta nueva?',
                showDenyButton: true,
                confirmButtonText: 'Crear',
                denyButtonText: 'Cancelar',
            })
                .then((result) => {
                    if (result.isConfirmed) {
                        Swal.fire({
                            title: 'Please select an account type',
                            html: `<section class="container-type">
                            <h2 class="mb-4 w-100">Account type</h2>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="accountType" id="SAVINGS"
                                    value="SAVINGS">
                                <label class="form-check-label mb-2" for="SAVINGS">
                                    SAVINGS
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="accountType" id="CHECKING"
                                    value="CHECKING">
                                <label class="form-check-label" for="CHECKING">
                                    CHECKING
                                </label>
                            </div>
                        </section>`
                        })
                            .then((result) => {
                                const selected = document.querySelector("input[name=accountType]:checked")
                                axios.post("/api/clients/current/accounts", `type=${selected.value}`)
                                    .then(response => {
                                        Swal.fire('Account saved!', '', 'success').then(response => {
                                            location.href = '../pages/accounts.html'
                                        })
                                    })
                            }).catch((error) => error.response.data)
                    } else {
                        Swal.fire('No se ah creado una nueva cuenta', '', 'info')
                    }
                }).catch((error) => error.response.data)
        }
    }

}
const app = createApp(options)
    .mount('#app')








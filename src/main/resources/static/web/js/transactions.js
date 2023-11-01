const { createApp } = Vue

const app = createApp({
    data() {
        return {
            firstName: '',
            client: [],
            accounts: [],
            origin: '',
            destination: '',
            amount: '',
            description: '',
            showForm1: true,
            showForm2: false  
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            this.client = JSON.parse(localStorage.getItem('client'))
            axios.get(`/api/clients/current/accounts`)
                .then(response => {
                    this.accounts = response.data

                })
        },
        newTransfer() {
            Swal.fire({
                title: 'Add a new transfer?',
                inputAttributes: {
                    autocapitalize: 'off',
                },
                showCancelButton: true,
                confirmButtonText: 'Yes',
                showLoaderOnConfirm: true,
                buttonColor: '#32a852',
                preConfirm: login => {
                    console.log(this.amount, this.description, this.origin, this.destination);
                    return axios
                        .post('/api/transactions',
                        `amount=${this.amount}&description=${this.description}&originAccountNumber=${this.origin}&destinationAccountNumber=${this.destination}`,)
                        .then(response => {
                            setTimeout(() => {
                                location.href = '../pages/transactions.html';
                            }, 2000)
                        })
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                            })
                        })
                }
            })
        },
        form1(){
            this.showForm1 = false
            this.showForm2 = true
        },
        form2(){
            this.showForm2 = false
            this.showForm1 = true
        },
        logout() {
            axios.post('/api/logout')
                .then(response => {
                    location.href = '/web/pages/index.html';
                })
                .catch(error => console.error(error))
          },
    }
}).mount("#app")

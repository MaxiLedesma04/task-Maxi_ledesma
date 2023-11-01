const app = Vue.createApp({
    data() {
        return {
            loans: [],
            originAccounts: null,
            amount: null,
            selectLoan: null,
            selectOriginAccount: null,
            selectPayments: 0,
            finalAmount: 0,
            clients:null,
        }
    },
    created() {
        this.loadData()
        this.loadClientes()
    },
    methods: {
        loadData() {
            axios.get('/api/clients/current/accounts')
                .then(response => {
                    this.originAccounts = response.data
                    console.log(this.originAccounts);

                }).catch(error => console.log(error))
        },
        loadClientes() {
            axios.get('/api/clients/current')
            .then(response =>{
            this.clients = response.data
            console.log(this.clients);
            this.loans = this.clients.loans
            
        })
        },
        PayAmount() {
            let mensaje;
            let opcion = confirm("Do you want to pay a loan?");
            console.log("Hola");
            if (opcion == true) {
                console.log(this.amount);
                console.log(this.loans.idClientLoan);
                axios.patch('/api/clients/current/loans/loanPayment', `loanId=${this.selectLoan}&accountId=${this.selectOriginAccount}&paymentAmount=${this.amount}`)
                    .then(response => {
                        location.href = "/web/public/pages/accounts.html"
                    })
                    .catch(error => {
                        console.log(error);
                        window.alert(error.response.data)
                    })
            } else {
                mensaje = "Cancel";
            }

        },
        logout() {
            axios.post(`/api/logout`)
                .then(response => console.log('signed out!!'))
                .then
            return (window.location.href = "../../index.html")
        },
    },

});

app.mount("#app");
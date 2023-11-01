const app = Vue.createApp({
    data() {
        return {
            loans: [],
            originAccounts: null,
            amount: null,
            selectLoan: {},
            selectOriginAccount: {},
            selectPayments:0,
            finalAmount:0,
            maxAmount:0,
        }
    },
    created() {
        this.loadData()
        this.loadLoans()
    },
    methods: {
        alerta() { 
            Swal.fire({
                title: 'Confirm loan request',
                showDenyButton: true,
                confirmButtonText: 'Confirm',
                denyButtonText: 'Cancel',
            })
                .then((result) => {
                    if (result.isConfirmed) {
                        let object = {
                            "id": this.selectLoan.id,
                            "amount": this.amount,
                            "payments": this.selectPayments,
                            "number": this.selectOriginAccount
                        }
                        axios.post("/api/loans", object)
                            .then(response => {
                                Swal.fire('Saved!', '', 'success')
                                    .then(response => {
                                        location.href = '../pages/accounts.html'
                                    })
                            })
                            .catch((error) => {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: error.response.data,
                                })
                            })
                    } else {
                        Swal.fire('Changes are not saved', '', 'info')
                    }
                })
        },
        loadData() {
            axios.get('/api/clients/current/accounts')
                .then(response => {
                    this.originAccounts = response.data
                    console.log(this.originAccounts);
                }).catch(error => console.log(error))
        },
        loadLoans() {
            axios.get('/api/loans')
                .then(response => {
                    this.loans = response.data
                    console.log(this.loans)
                }).catch(error => console.log(error))
        },

        logout() {
            axios.post(`/api/logout`)
                .then(response => console.log('signed out!!'))
                .then
            return (window.location.href = "../../index.html")
        },
    },
    computed: {
        calculoInteres(){
            if(this.selectPayments == 6){
                this.finalAmount = this.amount * 0.1
                return this.finalAmount;
            }
            else if(this.selectPayments == 12){
                this.finalAmount = this.amount * 0.15
                return this.finalAmount;
            }
            else if(this.selectPayments == 24){
                this.finalAmount = this.amount * 0.20
                return this.finalAmount;
            }
            else if(this.selectPayments == 36){
                this.finalAmount = this.amount * 0.25
                return this.finalAmount;
            }
            else if(this.selectPayments == 48){
                this.finalAmount = this.amount * 0.30
                return this.finalAmount;
            }
            else if(this.selectPayments == 60){
                this.finalAmount = this.amount * 0.35
                return this.finalAmount;
            }else{return 0};
        },
    },
});

app.mount("#app");
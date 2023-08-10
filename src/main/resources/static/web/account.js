const { createApp } = Vue
const url = ("http://localhost:8080/api/accounts/")
const options = {
    data() {
        return {
            accounts: [],
            parametroId: null,
            transactions: [],
        };
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            const parametro = location.search
            const nuevoparametro = new URLSearchParams(parametro)
            this.parametroId = nuevoparametro.get("id")
            console.log(this.parametroId)
            axios.get(url + this.parametroId)
                .then(response => {
                    this.accounts = response.data
                    this.transactions = this.accounts.transactions.sort((a,b)=> b.id - a.id)
                    console.log(this.transactions)
                    })
                .catch(error => console.error(error))
        },
    }

}
const app = createApp(options)
    .mount('#app')


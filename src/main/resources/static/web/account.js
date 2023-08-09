const { createApp } = Vue
const url = "http://localhost:8080/api/accounts/1"
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
            axios.get(url)
                .then(response => {
                    this.accounts = response.data
                    const parametro = location.search
                    const nuevoparametro = new URLSearchParams(parametro)
                    this.parametroId = nuevoparametro.get("id")
                    this.transactions = this.accounts.transactions.sort((a,b)=> b.id - a.id)
                    })
                .catch(error => console.error(error))
        },
    }

}
const app = createApp(options)
    .mount('#app')


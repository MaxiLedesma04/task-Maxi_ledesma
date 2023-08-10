const { createApp } = Vue
const url = "http://localhost:8080/api/clients/1"
const options = {
    data() {
        return {
            clients: [],
            clients_accounts: [],
            accounts: [],
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
                    console.log(this.clients_accounts)
                    const numberF = Intl.NumberFormat('es-US', {
                        style: 'currency',
                        currency: 'USD',
                        maximumSignificantDigits: 1
                    })
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
    }

}
const app = createApp(options)
    .mount('#app')








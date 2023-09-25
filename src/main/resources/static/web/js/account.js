const { createApp } = Vue
const url = "/api/clients/accounts/"
const options = {
    data() {
        return {
            accounts_client: [],
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
            axios.get(url+ this.parametroId)
                .then(response => {
                    this.accounts= response.data
                    console.log(this.accounts);
                    this.transactions_acc = this.accounts.transactions.sort((a,b)=> b.id - a.id)
                    console.log(this.transactions)
                    const numberF = Intl.NumberFormat('es-US', {
                        style: 'currency',
                        currency: 'USD',
                        maximumSignificantDigits: 1
                    })
                    const balance = numberF.format(this.accounts.balance)
                    this.accounts.balance = balance 

                    for(const transactions of this.transactions_acc){
                        const date = new Date(transactions.localdate);
                        const formattedDate = date.toLocaleString('es-ES', {
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                            hour: '2-digit',
                            minute: '2-digit',
                        });
                        const aux = {
                            id: transactions.id,
                            amount: numberF.format(transactions.amount),
                            balance: numberF.format(transactions.balance),
                            description: transactions.description,
                            localdate: formattedDate,
                            type: transactions.type,
                        }
                        this.transactions.push(aux)
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
    }

}
const app = createApp(options)
    .mount('#app')


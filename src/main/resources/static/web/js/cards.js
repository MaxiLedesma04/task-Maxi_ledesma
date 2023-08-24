const { createApp } = Vue
const url = "/api/client/current"
const options = {
    data() {
        return {
            cards: [],
            creditCards: [],
            debitCards:[],

        };
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(url)
                .then(response => {
                    this.cards = response.data.cards;
                    console.log(response)
                    console.log(this.cards)
                    this.creditCards = this.cards.filter(card => card.type == 'CREDIT');
                    this.debitCards = this.cards.filter(card => card.type == 'DEBIT');
                    
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
    }

}
const app = createApp(options)
    .mount('#app')








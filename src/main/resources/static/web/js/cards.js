const { createApp } = Vue
const url = "/api/clients/current/cards"
const options = {
    data() {
        return {
            cards: [],
            creditCards: [],
            debitCards:[],
            showForm: false,
            dateNao: new Date().toISOString().slice(0, 10),
        };
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            console.log(this.dateNao);
            axios.get(url)
                .then(response => {
                    this.cards = response.data.filter(card => card.active)
                    // console.log(response)
                    console.log(this.cards)
                    this.creditCards = this.cards.filter(card => card.type == 'CREDIT');
                    console.log(this.creditCards[0].fromDate);
                    this.debitCards = this.cards.filter(card => card.type == 'DEBIT');
                    
                })
                .catch(error => console.error(error))
        },



        borrar(id){           
            console.log(id)
        Swal.fire({title: '¿Quieres eliminar la tarjeta?',
        inputAttributes: {autocapitalize: 'off'},
        showCancelButton: true, 
        confirmButtonText: "Seguro",
        showLoaderOnConfirm: true,
         preConfirm: login =>{
            axios.patch('/api/clients/current/cards/deactivate', `id=${id}`)
            .then(response => {
                location.href = '/web/pages/cards.html';
                })
                .catch(error=> {
                    swal.fire({icon: 'error',
                text: error.response.data,
            confirmButtonColor: '#5b31be93',});
                });
            },
            allowOutsideClick: () => !Swal.isLoading(),
         });
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








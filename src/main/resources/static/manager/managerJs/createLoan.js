const app = Vue.createApp({
    data() {
        return {
           id: null,
           name: "",
           maxAmount: null,
           payments: [],
           interest: null,
        }
    },
    methods: {
        crearPrestamo() {
            Swal.fire({
                title: 'Confirm loan request',
                showDenyButton: true,
                confirmButtonText: 'Confirm',
                denyButtonText: 'Cancel',
            })
                .then((result) => {
                    if (result.isConfirmed) {
                        let object = {
                            "name": this.name,
                            "maxAmount": this.maxAmount,
                            "payments": this.payments,
                            "interes": this.interest,
                        }
                        console.log(object);
                        axios.post("/api/create/loans", object)
                        
                            .then(response => {
                                Swal.fire('Saved!', '', 'success')
                                    .then(response => {
                                        location.href = './manager.html'
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
                        Swal.fire('Los cambios no se guardaron', '', 'info')
                    }
                })
        },
        logout() {
            axios.post(`/api/logout`)
                .then(response => console.log('signed out!!'))
                .then
            return (window.location.href = "../../web/pages/index.html")
        },
    },
});

app.mount("#app");
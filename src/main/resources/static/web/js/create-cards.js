const app = Vue.createApp({
    data() {
      return {
        type: "",
        color: "",
        myCard: '',
      };
    },
    methods: {
      createCard() {
        console.log(this.type, this.color);
        axios.post("/api/clients/current/cards",`color=${this.color}&type=${this.type}`)
          .then((response) => {
            console.log("Tarjeta creada con éxito:", response.data);
            //  location.href = '/web/pages/cards.html';

          })
          .catch((error) => {
            console.error("Error al crear la tarjeta:", error);
          });
      },
      logout() {
        axios.post('/api/logout')
            .then(response => {
                location.href = '/web/pages/index.html';
            })
            .catch(error => console.error(error))
      },
    },
  });
  
  app.mount("#app");
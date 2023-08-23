const {createApp} = Vue
const url = "http://localhost:8080/rest/clients"
const options ={
    data(){
        return{
            clients: [],
            firstName: "",
            lastName:"",
            email:"",
            json:null,
        };
    },
    created(){
        this.loadData()
    },
    methods: {
        loadData(){
            axios.get("http://localhost:8080/rest/clients")
            .then(response => {
                this.clients = response.data._embedded.clients
                this.json = JSON.stringify(response.data, null, 1);
            })
            .catch(error => console.error(error))
        },

        inputCheck() {
            if (this.firstName && this.lastName && this.email){
                this.addClient();
            } else{
                alert("Not data file");
            }

        },

        addClient(){
            let clientNew ={
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
            }
            axios.post(url, clientNew)
            .then (response =>{
                this.clients.push(response.data);
                this.firstName = "";
                this.lastName = "";
                this.email = "";
                this.loadData();
            })
            .catch(error => console.error(error))
        }
    }

}
const app2 = createApp(options)
.mount('#app2')
import Vue from "vue"
import Vuex from "vuex";
import { auth } from "./modules/authen.js";

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    auth,
    
  },
  state: {
    baseUrl: "http://localhost:8080/api",
  },
  mutations: {
  },
});
import { createStore } from 'vuex'

const store = createStore({
  state() {
    return {
      viewer: []
    }
  },
})

export default store
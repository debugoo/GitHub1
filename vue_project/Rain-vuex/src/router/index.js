import Vue from 'vue'
import Router from 'vue-router'
import Demo from 'components/demo-vuex'
import Demo2 from 'components/demo-vuex2'
import table from '../components/table'

Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'demo',
      component: table
    },
    {
      path: '/demo2',
      name: 'demo2',
      component: Demo2
    },
    
  ]
})

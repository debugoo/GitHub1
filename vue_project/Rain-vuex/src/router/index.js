import Vue from 'vue'
import Router from 'vue-router'
import Demo from 'components/demo-vuex'
import Demo2 from 'components/demo-vuex2'
import table from '../components/table'

Vue.use(Router)
export default new Router({
  mode: 'history',
  routes: [{
      path: '/',
      name: 'demo',
      component: table
    },
    {
      path: '/ueditor',
      name: 'demo2',
      component: resolve => require(['../components/ueditor/index.vue'], resolve)
    },
    {
      path: '/Highlight',
      name: 'demo2',
      component: resolve => require(['../components/Highlight/index.vue'], resolve)
    },
  ]
})

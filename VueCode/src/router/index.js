import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manager.vue'
import User from "@/views/User.vue";
import Home from "@/views/Home.vue";
import login from "@/views/Login.vue";
import register from "@/views/Register.vue";
import Person from "@/views/Person.vue";
import FilePage from "@/views/FilePage.vue";
import Role from "@/views/Role.vue";
import {Menu} from "element-ui";
import MenuManager from "@/views/MenuManager.vue";
import UpdatePwd from "@/views/UpdatePwd.vue";

Vue.use(VueRouter)

const routes = [

    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/login',
        name: 'login',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: login
    },
    {
        path: '/register',
        name: 'register',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: register
    },
    {
        path: '/404',
        name: '404',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/404.vue')
    },

    {
        path: '/front',
        name: 'front',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Front.vue'),
        children:[
            {
                path: 'frontHome',
                name: 'FrontHome',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ '../views/front/Home.vue')
            },{
                path: 'frontPerson',
                name: 'FrontPerson',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ '../views/front/FrontPerson.vue')
            },{
                path: 'frontUpdatePwd',
                name: 'FrontUpdatePwd',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ '../views/front/FrontUpdatePwd.vue')
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export const resetRouter=()=>{
    router.matcher=new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}

//刷新页面会重置路由
export const setRouters = () => {
    const curRoutes = router.getRoutes().map(v => v.name)
    if (curRoutes.includes("Manage")) {
        return
    }
    const user = JSON.parse(localStorage.getItem("user"))
    console.log(user && user.menus + "setRouters ")
    if (user && user.menus.length > 0) {
        const manageRoute = {
            path: '/',
            component: HomeView,
            redirect: "/home",//重定向
            name: "Manage",
            children: []
        }
        const menus = user.menus
        menus.forEach(item => {
            if (item.path) {
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    meta: {},
                    component: () => import(/* webpackChunkName: "about" */ '../views/' + item.component + '.vue')
                }
                manageRoute.children.push(itemMenu)
            } else if (item.children.length > 0) {
                item.children.forEach(childrenItem => {
                    if (childrenItem.path) {
                        let itemMenu = {
                            path: childrenItem.path.replace("/", ""),
                            name: childrenItem.name,
                            meta: {},
                            component: () => import(/* webpackChunkName: "about" */ '../views/' + childrenItem.component + '.vue')
                        }
                        manageRoute.children.push(itemMenu)
                    }
                })
            }

        })
        manageRoute.children.push({path: "person", name: '个人信息', component: Person})
        manageRoute.children.push({path: "updatepwd", name: '修改密码', component: UpdatePwd})
        router.addRoute(manageRoute)

    }
}

setRouters()
router.beforeEach((to, from, next) => {
   const user= localStorage.getItem("user")
    if(!to.matched.length){
        if(user){
            next("/404")
        }else{
            next("/login")
        }
    }
    next()
})
export default router

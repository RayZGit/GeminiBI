export default [
  {
    path: '/user',
    layout: false,
    routes: [{ name: 'Login', path: '/user/login', component: './User/Login' }],
  },
  { path: '/welcome', name: 'Welcome', icon: 'smile', component: './Welcome' },
  {
    path: '/admin',
    name: 'Admin',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/sub-page' },
      { path: '/admin/sub-page', name: 'Sub-Page', component: './Admin' },
    ],
  },
  { name: 'Search Table', icon: 'table', path: '/list', component: './TableList' },
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];

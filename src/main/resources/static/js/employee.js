let employee = employee1;

Vue.component('getOneEmployee', {
    props:['employee'],
    data: function (){
        return {
            employee_id: '',
            first_name: '',
            last_name: '',
            department_id:'',
            job_title:'',
            gender:'',
            date_of_birth:''
        }
    },
    template:
        '<div>' +
        '{{ employee.employee_id }}' +
        '{{ employee.first_name }} {{employee.last_name}} ' +
        '{{employee.department_id}} {{employee.job_title}} ' +
        '{{employee.gender}} {{employee.date_of_birth}} ' +
        '</div>'
})


var app1 = new Vue({
    el: '#app1',
    template: '<getOneEmployee :employee1="employee" />',
    data: {
        employee:[]
    }
});

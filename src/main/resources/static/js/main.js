
var employeeApi = Vue.resource('/employee{/id}');

Vue.component('employee-form', {
    props:['employees','employee'],
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
        '<input type = "index" placeholder="Enter employee ID" v-model="employee_id" />'+
        '<input type = "text" placeholder="Enter first_name" v-model="first_name" />'+
        '<input type = "text" placeholder="Enter last_name" v-model="last_name" />'+
        '<input type = "text" placeholder="Enter department_id" v-model="department_id" />'+
        '<input type = "text" placeholder="Enter job_title" v-model="job_title" />'+
        '<input type = "text" placeholder="Enter gender" v-model="gender" />'+
        '<input type = "text" placeholder="Enter date_of_birth" v-model="date_of_birth" />'+
        '<input type = "button" value="Add" @click="add" />'+
        '<input type = "button" value="Delete" @click="del" />'+
        '<input type = "button" value="Get one" @click="getOne" />'+
        '</div>',
    methods:{
        add: function (){
            var employee = {
                employee_id: this.employee_id,
                first_name: this.first_name,
                last_name:  this.last_name,
                department_id:  this.department_id,
                job_title:  this.job_title,
                gender:  this.gender,
                date_of_birth:  this.date_of_birth,
            };

            employeeApi.save({}, employee).then(result =>
                result.json().then(data => {
                    this.employees.push(data);
                    this.employee_id = ''
                    this.first_name = ''
                    this.last_name = ''
                    this.department_id = ''
                    this.job_title = ''
                    this.gender = ''
                    this.date_of_birth = ''
                })
            )
        },
        del: function (){
            employeeApi.remove({id:this.employee_id}).then(result =>{
                if(result.ok){
                    this.employees.splice(this.employees.indexOf(this.employee) , 1)
                    this.employee_id = ''
                }
            })
        },
        getOne: function (){

        }
    }
});

Vue.component('employee_row',{
    props: ['employee', 'employees'],
    template:
        '<div>' +
        '<i>({{employee.employee_id}})</i>' +
        '{{employee.first_name}} {{employee.last_name}} ' +
        '{{employee.department_id}} {{employee.job_title}} ' +
        '{{employee.gender}} {{employee.date_of_birth}}' +
        // '<span style = "position: absolute; right: 0"> '+
        //     '<input type="button" value="Delete" @click="del" />' +
        // '</span>'+
        '</div>'
})

Vue.component('employees-list', {
    props:['employees'],
    template:
        '<div>' +
        '<employee-form :employees = "employees" />' +
        '<employee_row v-for="employee in employees" :key="employee.employee_id" :employee="employee"/>' +
        '</div>',
    created: function(){
        employeeApi.get().then(result =>
            result.json().then(data =>
                data.forEach(employee => this.employees.push(employee))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<employees-list :employees="employees" />',
    data: {
        employees: []
    }
});
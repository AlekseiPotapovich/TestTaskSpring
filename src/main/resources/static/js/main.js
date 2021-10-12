function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].employee_id === id) {
            return i;
        }
    }

    return -1;
}
var emID;
let employee1;

var employeeApi = Vue.resource('/employee{/id}');

Vue.component('employee-form', {
    props:['employees','employeeAttr'],
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
    watch:{
        employeeAttr: function (newVal, oldVal){
            this.employee_id = newVal.employee_id;
            this.first_name = newVal.first_name;
            this.last_name = newVal.last_name;
            this.department_id = newVal.department_id;
            this.job_title = newVal.job_title;
            this.gender = newVal.gender;
            this.date_of_birth = newVal.date_of_birth;
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
        '<input type = "button" value="Get List Of Employees" @click="getAll" />'+
        //'<input type = "button" value="Delete" @click="del" />'+

        '<input type = "button" value="Update" @click="update" />'+

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
        // del: function (){
        //     employeeApi.remove({id:this.employee_id}).then(result =>{
        //         if(result.ok){
        //             this.employees.splice(this.employees.indexOf(this.employee) , 1)
        //             this.employee_id = ''
        //         }
        //     })
        // },

        update: function (){
            var employee = {
                employee_id: this.employee_id,
                first_name: this.first_name,
                last_name:  this.last_name,
                department_id:  this.department_id,
                job_title:  this.job_title,
                gender:  this.gender,
                date_of_birth:  this.date_of_birth,
            };
            employeeApi.update({id: emID}, employee).then(result =>
                result.json().then(data => {
                    var index = getIndex(this.employees, this.employee_id);
                    this.employees.splice(index, 1, data);
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
        getAll: function (){
            app = open('employee');
        }
    }
});

Vue.component('employee_row',{
    props: ['employees', 'employee', 'editMethod'],
    template:
        '<div>' +
        '<i>({{ employee.employee_id }})</i>' +
        '{{ employee.first_name }} {{employee.last_name}} ' +
        '{{employee.department_id}} {{employee.job_title}} ' +
        '{{employee.gender}} {{employee.date_of_birth}}' +
        '<span style="position: absolute; right: 0">' +
            '<input type="button" value="Edit" @click="edit" />' +
            '<input type = "button" value="Get one" @click="getOne" />'+
            '<input type="button" value="X" @click="del" />' +
        '</span>' +
        // '<span style = "position: absolute; right: 0"> '+
        //     '<input type="button" value="Delete" @click="del" />' +
        // '</span>'+
        '</div>',
    methods: {
        edit: function (){
            emID = this.employee.employee_id;
            this.editMethod(this.employee);
        },
        getOne: function (){


            employee1 = {
                employee_id: this.employee.employee_id,
                first_name: this.employee.first_name,
                last_name:  this.employee.last_name,
                department_id:  this.employee.department_id,
                job_title:  this.employee.job_title,
                gender:  this.employee.gender,
                date_of_birth:  this.employee.date_of_birth,
            };
            emID = this.employee.employee_id;
            app = open('employee.html');
            //app1 = open('employee/' + this.employee.employee_id);
            //this.employee_id = '';

        },
        del: function (){
            employeeApi.remove({id :this.employee.employee_id}).then(result =>{
                if(result.ok){

                    this.employees.splice(this.employees.indexOf(this.employee) , 1)
                    //this.employee_id = ''
                }
            })
        }
    }
})

Vue.component('employees-list', {
    props:['employees'],
    data: function (){
        return{
            employee: ''
        }
    },
    template:
        '<div>' +
        '<employee-form :employees = "employees" :employeeAttr="employee" />' +
        '<employee_row v-for="employee in employees" :key="employee.employee_id" :employee="employee" ' +
            ' :editMethod="editMethod" :employees="employees" />' +
        '</div>',
    created: function(){
        employeeApi.get().then(result =>
            result.json().then(data =>
                data.forEach(employee => this.employees.push(employee))
            )
        )
    },

    methods:{
        editMethod: function (employee){
            this.employee = employee;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<employees-list :employees="employees" />',
    data: {
        employees: []
    }
});

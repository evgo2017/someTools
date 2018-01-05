new Vue({
	el: '#todoApp',
	data: {
		newTodoText: '',
		showAchieves: false,
		showHelp: false,
		showTrash: false,
		todos: [
			{ title: '测试数据 001' },
			{ title: '测试数据 002' },
			{ title: '测试数据 003' }
		],
		achieves: [],
		trash: []
	},
	methods: {
		addNewTodo: function () {
			this.todos.push({ title: this.newTodoText });
		},
		achieve: function (index, value) {
			this.todos.splice(index, 1);
			this.$set(this.achieves, this.achieves.length ,{ title: value });
		},
		achieveAll : function () {
			this.achieves = this.achieves.concat(this.todos);
			this.todos= [];
		},
		restore: function (index, value) {
			this.achieves.splice(index, 1);
			this.$set(this.todos, this.todos.length ,{ title: value });
		},
		abandon: function (index, value, arr) {
			if(arr=='todos') this.todos.splice(index, 1);
			else if(arr=='achieves') this.achieves.splice(index, 1);
			this.$set(this.trash, this.trash.length ,{ title: value });
		},
		/*对所有元素操作*/
		deleteAll : function () {
			this.trash = [];
		}
		
	}
})
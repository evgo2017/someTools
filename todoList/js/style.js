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
		/*获取新数据，“添加”按钮*/
		addNewTodo: function () {
			this.todos.push({ title: this.newTodoText })
		},
		/*对单个元素操作*/
		changeDataArr: function (index, value, fromArr, toArr) {
			this[fromArr].splice(index, 1);
			this.$set(this[toArr], this[toArr].length, { title: value });
		},
		/*对所有元素操作*/
		changeAllDataArr: function (fromArr, toArr) {
			if(toArr!='') {
				this[toArr] = this[toArr].concat(this[fromArr]);
			}
			this[fromArr] = [];
		}
	}
})

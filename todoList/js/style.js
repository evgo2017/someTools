new Vue({
	el: '#todoApp',
	data (){
		return {
			newTodoText: '',
			showAchieves: false,
			showHelp: false,
			showTrash: false,
			todos: [
				{ title: '测试数据 001'},
				{ title: '测试数据 002'},
				{ title: '测试数据 003'}
			],
			achieves: [],
			trash: []
		}
	},
	components: {
		'my-exhibition': {
			props: {
				fromarr :{ /*只要from后面有大写就失败了。。*/
					type: Array
				}
			},
			template: '\
			<div>\
				<ul>\
					<li v-for="item in fromarr">\
						<input type="text" v-model="item.title">\
						<button class="btn btn-danger" @click="changeDataArr(index, item.title, fname, tname)">丢弃</button>\
					</li>\
				</ul>\
			</div>',
		}
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
			alert(12)
			if(toArr!='') {
				this[toArr] = this[toArr].concat(this[fromArr]);
			}
			this[fromArr] = [];
		}
	}
})
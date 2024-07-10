var i = 0;
function lunbo() {
	var obj = document.getElementById("lunbo" + i);
	obj.style.display = "none"; 
	i++;
	if (i > 4) {
		i = 0;
	}
	var nextObj = document.getElementById("lunbo" + i);
	nextObj.style.display = "block"; 
}
function showLoginPage() {
	var loginPage = document.getElementById("loginPage");
	loginPage.style.display = "block";
	document.getElementById("overlay").classList.add("show");
	document.getElementById("loginPage").style.display = "block";
}
function closeLoginPage() {
	var loginPage = document.getElementById("loginPage");
	loginPage.style.display = "none"; 
	document.getElementById("overlay").classList.remove("show");
	document.getElementById("loginPage").style.display = "none";
}
setInterval(lunbo, 3000);
function submitSearch() {
	// 获取输入框的值
	var actor = document.getElementById("actor").value;

	// 将输入框的值设置到搜索表单的输入框中
	document.getElementById("actorInput").value = actor;

	// 提交搜索表单
	document.getElementById("searchForm").submit();
}
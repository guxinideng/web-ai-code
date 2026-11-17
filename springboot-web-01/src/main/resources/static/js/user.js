// 对应后端 User 实体类的字段：
// id、username、password、name、age、updateTime
// 前端通过 GET /list 拿到 JSON 数组，渲染成表格

const tbody = document.getElementById('tbody');
const tip = document.getElementById('tip');
const count = document.getElementById('count');
const keywordInput = document.getElementById('keyword');

let allUsers = []; // 缓存完整数据，搜索时在前端过滤

// 请求后端数据
async function loadUsers() {
    showTip('加载中...');
    try {
        const resp = await fetch('/list');
        if (!resp.ok) {
            throw new Error('HTTP ' + resp.status);
        }
        allUsers = await resp.json();
        render();
    } catch (e) {
        console.error(e);
        showTip('数据加载失败：' + e.message, true);
        tbody.innerHTML = '';
        count.textContent = '';
    }
}

// 根据搜索关键字渲染表格
function render() {
    const kw = keywordInput.value.trim().toLowerCase();
    const list = allUsers.filter(u =>
        !kw ||
        (u.name || '').toLowerCase().includes(kw) ||
        (u.username || '').toLowerCase().includes(kw)
    );

    if (list.length === 0) {
        tbody.innerHTML = '';
        showTip('没有符合条件的用户');
        count.textContent = '';
        return;
    }

    hideTip();
    tbody.innerHTML = list.map(u => `
        <tr>
            <td>${escapeHtml(u.id)}</td>
            <td>${escapeHtml(u.username)}</td>
            <td>${maskPassword(u.password)}</td>
            <td>${escapeHtml(u.name)}</td>
            <td>${escapeHtml(u.age)}</td>
            <td>${formatTime(u.updateTime)}</td>
        </tr>
    `).join('');
    count.textContent = `共 ${list.length} 位用户`;
}

// 密码不在页面明文展示
function maskPassword(pwd) {
    return pwd ? '******' : '';
}

// LocalDateTime 序列化后形如 2024-07-15T15:05:45，把中间的 T 换成空格
function formatTime(t) {
    return t ? escapeHtml(String(t).replace('T', ' ')) : '';
}

// 防止数据里带有 HTML 标签被当成代码执行（XSS）
function escapeHtml(v) {
    if (v === null || v === undefined) return '';
    return String(v)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}

function showTip(msg, isError) {
    tip.textContent = msg;
    tip.className = 'tip' + (isError ? ' error' : '');
    tip.style.display = 'block';
}

function hideTip() {
    tip.style.display = 'none';
}

// 事件绑定
document.getElementById('btnRefresh').addEventListener('click', loadUsers);
keywordInput.addEventListener('input', render);

// 页面打开后自动加载
loadUsers();

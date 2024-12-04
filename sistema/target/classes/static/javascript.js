document.addEventListener('barra', () => {
	const slider = document.getElementById('myRange');
	const labels = document.querySelectorAll('.slider-labels span');

	slider.addEventListener('input', () => {
		const value = slider.value;
		labels.forEach((label, index) => {
			if (index <= value) {
				label.style.color = 'red';
			} else {
				label.style.color = 'black';
			}
		});
	});
});

const header = document.getElementById("myHeader");

// Define a altura a partir da qual o header deve ficar fixo
const stickyHeight = 100;

// Função que é chamada toda vez que o usuário rolar a página
window.onscroll = function() {
	if (window.pageYOffset > stickyHeight) {
		header.classList.add("fixed");
	} else {
		header.classList.remove("fixed");
	}
};

// Diário Pessoal

// Função para salvar a anotação
function saveNote() {
	const date = document.getElementById('date').value;
	const note = document.getElementById('note').value;
	const userId = 1;

	if (!date || !note) {
		alert('Por favor, preencha a data e a anotação!');
		return;
	}

	const noteData = {
		data_postagem: date,
		conteudo: note,
		usuario: { id: userId }
	};

	// Salva a anotação no localStorage (apenas para armazenamento local no navegador)
	localStorage.setItem(`note-${date}`, JSON.stringify(noteData));

	// Envia os dados para o backend (Spring Boot)
	fetch('/postagem', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(noteData)  // Envia o objeto noteData para o backend
	})
		.then(response => {
			if (response.ok) {
				return response.json();  // Se a resposta for bem-sucedida, retorna o JSON da resposta
			} else {
				throw new Error('Erro ao salvar a anotação no servidor');
			}
		})
		.then(response => response.json())
		.then(data => {
			alert('Anotação salva com sucesso no banco de dados!');
			document.getElementById('date').value = '';
			document.getElementById('note').value = '';
		})
		.catch(error => {
			console.error('Erro ao salvar a anotação:', error);
			alert('Anotação salva com sucesso no banco de dados!');
		});
}
// Pop up

window.onload = function() {
	document.getElementById("popup").classList.add("active");
};

// Função para fechar o pop-up
function closePopup() {
	document.getElementById("popup").classList.remove("active");
}

//login

document.addEventListener("DOMContentLoaded", function() {
	const loggedInUser = JSON.parse(localStorage.getItem("user"));
	const cadastroItem = document.getElementById("logged1");
	const loginItem = document.getElementById("logged2");
	const logoutItem = document.getElementById("logout");
	const barra = document.getElementById("barra");

	// Verifica se o usuário está logado
	if (loggedInUser && loggedInUser.success) {
		// Esconde "Cadastro" e "Login", mostra "Logout"
		cadastroItem.style.display = "none";
		loginItem.style.display = "none";
		logoutItem.style.display = "block";


		// Configura o botão de logout
		logoutItem.addEventListener("click", function() {
			// Remove os dados do usuário do localStorage
			localStorage.removeItem("user");
			alert("Você saiu da conta!");
			// Atualiza a página ou redireciona
			window.location.reload();
		});
	} else {
		// Mostra "Cadastro" e "Login", esconde "Logout"
		cadastroItem.style.display = "block";
		loginItem.style.display = "block";
		logoutItem.style.display = "none";

	}
});






function login() {

	const email = document.getElementById("email_login").value;
	const senha = document.getElementById("senha_login").value;

	const dadosLogin = {
		email: email,
		senha: senha
	};

	fetch('http://localhost:8080/index/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(dadosLogin)
	})
		.then(response => response.json())
		.then(data => {
			if (data) {
				alert("Login bem-sucedido!");
				// Aqui você pode redirecionar o usuário ou mostrar uma mensagem de sucesso
				localStorage.setItem('user', JSON.stringify(data));
				window.location.href = "index.html";
			} else {
				alert("Usuário ou senha inválidos!");
			}
		})
		.catch(error => {
			console.error("Erro ao tentar logar:", error);
		});
}

// Função para realizar o cadastro
document.getElementById("cadastro-form").addEventListener("submit", function(event) {
	event.preventDefault(); // Impede o envio padrão do formulário

	var nome = document.getElementById("nome_cad").value;
	var email = document.getElementById("email_cad").value;
	var senha = document.getElementById("senha_cad").value;

	var dadosCadastro = {
		nome: nome,
		email: email,
		senha: senha
	};

	// Envia os dados do cadastro para o backend via AJAX
	fetch('index/cadastrar', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(dadosCadastro)
	})
		.then(response => response.json())
		.then(data => {
			if (data) {
				alert("Cadastro realizado com sucesso!");
				// Redireciona para a página de login ou para outra página
				window.location.href = "index.html";
			}
		})
		.catch(error => {
			console.error("Erro ao tentar cadastrar:", error);
		});
});


//histórico

function carregarPostagens() {
    const usuario = JSON.parse(localStorage.getItem('user'));
    const usuarioId = usuario.id;

    if (!usuario || !usuario.success) {
        alert("Usuário não autenticado. Redirecionando para o login.");
        window.location.href = "login.html";
        return;
    }

    fetch(`http://localhost:8080/postagem/usuario/${usuarioId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição: ' + response.statusText);
            }
            return response.json();
        })
        .then(postagens => {
            const tabelaPostagens = document.getElementById('nota-lista');
            tabelaPostagens.innerHTML = ''; // Limpa tabela antes de popular novamente
            
            if (postagens.length > 0) {
                postagens.forEach(postagem => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${postagem.id}</td>
                        <td>${postagem.conteudo}</td>
                        <td>${postagem.data_postagem}</td>
                    `;
                    tabelaPostagens.appendChild(row);
                });
            } else {
                tabelaPostagens.innerHTML = '<tr><td colspan="3">Nenhuma postagem encontrada.</td></tr>';
            }
        })
        .catch(error => {
            console.error('Erro ao carregar postagens:', error);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    carregarPostagens(); // Chama a função automaticamente ao carregar a página
});	7
// Função para listar todos os livros
/*async function listarPostagens() {
	try {
		const usuarioId = JSON.parse(localStorage.getItem("user")).id;
		const response = await fetch("http://localhost:8080/postagem/usuario/${usuarioId}");
		const postagem = await response.json();

		listaPostagens.innerHTML = "";
		postagem.forEach((postagem) => {
			const li = document.createElement("li");
			li.textContent = `Data: ${postagem.data_postagem} - Post: ${livro.conteudo}`;
			listaPostagens.appendChild(li);
		});
	} catch (error) {
		console.error("Erro ao carregar as postagens:", error);
	}
}

// Carregar a lista de livros ao abrir a página
listarLivros();

const apiURL = 'http://localhost:8080/postagem/usuario/';

// Função para buscar notas do backend
function carregarNotas() {
	fetch(apiURL)
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao buscar notas');
			}
			return response.json();
		})
		.then(data => {
			console.log('Dados recebidos:', data);
			const tabela = document.getElementById('nota-lista');
			tabela.innerHTML = ''; // Limpa a tabela antes de renderizar

			// Adiciona cada nota como uma linha na tabela
			data.forEach(nota => {
				const linha = document.createElement('tr');
				linha.innerHTML = `
                    <td>${nota.id}</td>
                    <td>${nota.conteudo}</td>
                    <td>${nota.data_postagem}</td>
                `;
				tabela.appendChild(linha);
			});
		})
		.catch(error => {
			console.error('Erro ao salvar a anotação:', error);
			alert('Erro ao salvar a anotação no banco de dados: ' + error.message);
		});
	console.log('Fetch URL:', apiURL);
}
document.addEventListener("DOMContentLoaded", function() {
	carregarNotas();
});
// Carrega as notas ao carregar a página

*/

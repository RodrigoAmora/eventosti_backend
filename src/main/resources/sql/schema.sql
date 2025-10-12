-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS eventos_ti
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE eventos_ti;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    has_error VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela de perfis (roles)
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela de relacionamento entre usuários e perfis
CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela de endereços
CREATE TABLE IF NOT EXISTS enderecos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    lat DOUBLE,
    lng DOUBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela de eventos
CREATE TABLE IF NOT EXISTS eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    site VARCHAR(255),
    data_inicio DATE,
    data_fim DATE,
    tipo_evento VARCHAR(50),
    status VARCHAR(50),
    endereco_id BIGINT,
    FOREIGN KEY (endereco_id) REFERENCES enderecos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela para tokens de redefinição de senha
CREATE TABLE IF NOT EXISTS reset_password_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela para blacklist de tokens JWT
CREATE TABLE IF NOT EXISTS black_list_access_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(500) NOT NULL,
    expiry_date DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserir perfis iniciais
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_USER');

-- Criar usuário admin padrão (senha: admin123)
INSERT IGNORE INTO usuarios (nome, login, password) 
VALUES ('Administrador', 'admin', '$2a$10$XURPShQNCsLjp1ESc7laO.1F1Yr9aGX5MPu3JrDE6BKrcN9pgNQm2');

-- Associar perfil de admin ao usuário admin
INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM usuarios u, roles r 
WHERE u.login = 'admin' AND r.name = 'ROLE_ADMIN';

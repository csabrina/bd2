# Diferença entre `1_create_function_calculate_ranking.sql` e `create_function_calculate_ranking.sql`

## Arquivo: `1_create_function_calculate_ranking.sql`

- **Uso de `LEFT JOIN`**: Retorna todos os times, independentemente de terem participado de uma partida ou não.

## Arquivo: `2_create_function_calculate_ranking.sql`

- **Uso de `INNER JOIN`**: Retorna apenas os times que participaram de pelo menos uma partida, seja como time da casa ou como time visitante.

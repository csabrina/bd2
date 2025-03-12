CREATE OR REPLACE FUNCTION public.calculate_ranking(
    league_id INT,
    season TEXT
)
RETURNS TABLE(
    team_id INT,
    team_name TEXT,
    points INT
) AS
$$
BEGIN
RETURN QUERY
SELECT
    t.id AS team_id,
    t.team_long_name AS team_name,
    COALESCE(
        SUM(
            CASE
                WHEN m.home_team_api_id = t.team_api_id THEN
                    CASE
                        WHEN m.home_team_goal > m.away_team_goal THEN 3
                        WHEN m.home_team_goal = m.away_team_goal THEN 1
                        ELSE 0
                    END
                WHEN m.away_team_api_id = t.team_api_id THEN
                    CASE
                        WHEN m.away_team_goal > m.home_team_goal THEN 3
                        WHEN m.home_team_goal = m.away_team_goal THEN 1
                        ELSE 0
                    END
                ELSE 0
            END
        ), 0
    ) AS points
FROM
    public."Team" t
    INNER JOIN public."match" m
    ON m.home_team_api_id = t.team_api_id OR m.away_team_api_id = t.team_api_id
WHERE
    m.league_id = league_id
    AND m.season = season
GROUP BY
    t.id, t.team_long_name
ORDER BY
    points DESC;
END;
$$ LANGUAGE plpgsql;

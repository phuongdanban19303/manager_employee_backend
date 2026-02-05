CREATE OR REPLACE FUNCTION "public"."fn_contract_termination_detail"("p_form_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "team" varchar, "termination_date" date, "termination_reason" text, "receiver_id" int8, "receiver_name" varchar, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    f.id,
    f.employee_id,
    e.employee_code,
    e.full_name,
    e.team,
    f.termination_date,
    f.termination_reason,
    f.receiver_id,
    u.fullname AS receiver_name,
    f.status,
    f.submit_date,
    f.approve_date,
    f.leader_note,
    f.created_by,
    f.created_at,
    f.updated_by,
    f.updated_at
FROM form_contract_termination f
         JOIN tbl_employee e ON e.id = f.employee_id
         LEFT JOIN tbl_user u ON u.id = f.receiver_id
WHERE f.id = p_form_id;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_contract_termination_list_pending"()
  RETURNS TABLE("id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "termination_date" date, "receiver_id" int8, "receiver_name" varchar, "status" varchar, "submit_date" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    f.id,
    f.employee_id,
    e.employee_code,
    e.full_name,
    f.termination_date,
    f.receiver_id,
    u.fullname AS receiver_name,
    f.status,
    f.submit_date
FROM form_contract_termination f
         JOIN tbl_employee e ON e.id = f.employee_id
         LEFT JOIN tbl_user u ON u.id = f.receiver_id
WHERE f.status = 'PENDING'
ORDER BY f.submit_date DESC, f.created_at DESC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_certificates"("p_user_id" int8, "p_employee_id" int8)
  RETURNS TABLE("id" int4, "employee_id" int4, "name" varchar, "issue_date" date, "content" text, "field_url" varchar, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    c.id,
    c.employee_id,
    c.name,
    c.issue_date,
    c.content,
    c.field_url,
    c.created_by,
    c.created_at,
    c.updated_by,
    c.updated_at
FROM employee_certificates c
WHERE c.employee_id = p_employee_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_detail"("p_user_id" int8, "p_employee_id" int8)
  RETURNS TABLE("id" int8, "employee_code" varchar, "full_name" varchar, "gender" varchar, "date_of_birth" date, "address" varchar, "team" varchar, "avatar_url" varchar, "identity_number" varchar, "phone" varchar, "email" varchar, "status" varchar, "status_updated_at" timestamp, "terminated_at" date, "termination_reason" varchar, "archive_date" date, "archive_number" varchar, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    e.id,                 -- 0
    e.employee_code,      -- 1
    e.full_name,          -- 2
    e.gender,             -- 3
    e.date_of_birth,      -- 4
    e.address,            -- 5
    e.team,               -- 6
    e.avatar_url,         -- 7
    e.identity_number,    -- 8
    e.phone,              -- 9
    e.email,              -- 10
    e.status,             -- 11
    e.status_updated_at,  -- 12
    e.terminated_at,      -- 13
    e.termination_reason, -- 14
    e.archive_date,       -- 15
    e.archive_number,     -- 16
    e.created_by,         -- 17
    e.created_at,         -- 18
    e.updated_by,         -- 19
    e.updated_at          -- 20
FROM tbl_employee e
WHERE e.id = p_employee_id;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_family_relations"("p_user_id" int8, "p_employee_id" int8)
  RETURNS TABLE("id" int4, "employee_id" int4, "full_name" varchar, "gender" int4, "date_of_birth" date, "identity_card_number" varchar, "relationship" varchar, "address" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    fr.id,
    fr.employee_id,
    fr.full_name,
    fr.gender,
    fr.date_of_birth,
    fr.identity_card_number,
    fr.relationship,
    fr.address,
    fr.created_by,
    fr.created_at,
    fr.updated_by,
    fr.updated_at
FROM employee_family_relations fr
WHERE fr.employee_id = p_employee_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_list_approved_by_user"("p_user_id" int8)
  RETURNS TABLE("id" int8, "employee_code" varchar, "full_name" varchar, "team" varchar, "status" varchar) AS $BODY$
DECLARE
v_role VARCHAR;
    v_team VARCHAR;
BEGIN
    /* ===============================
       1. USER INFO
       =============================== */
SELECT u.role, u.team
INTO v_role, v_team
FROM tbl_user u
WHERE u.id = p_user_id
  AND u.status = 'ACTIVE';

-- User không hợp lệ → trả empty
IF v_role IS NULL THEN
        RETURN;
END IF;

    /* ===============================
       2. LIST APPROVED EMPLOYEE
       =============================== */
RETURN QUERY
SELECT
    e.id,
    e.employee_code,
    e.full_name,
    e.team,
    e.status
FROM tbl_employee e
WHERE e.status = 'APPROVED'
  AND (
    (v_role = 'TEAM_LEADER' AND e.team = v_team)
        OR v_role = 'MANAGER'
    );
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_list_by_user"("p_user_id" int8)
  RETURNS TABLE("id" int8, "employee_code" varchar, "full_name" varchar, "team" varchar, "status" varchar) AS $BODY$
DECLARE
v_role VARCHAR;
    v_team VARCHAR;
BEGIN
    /* ===============================
       1. USER INFO
    =============================== */
SELECT u.role, u.team
INTO v_role, v_team
FROM tbl_user u
WHERE u.id = p_user_id
  AND u.status = 'ACTIVE';

-- User không hợp lệ → trả empty list
IF v_role IS NULL THEN
        RETURN;
END IF;

    /* ===============================
       2. LIST EMPLOYEE
    =============================== */
RETURN QUERY
SELECT
    e.id,
    e.employee_code,
    e.full_name,
    e.team,
    e.status
FROM tbl_employee e
WHERE e.status IN (
                   'DRAFT',
                   'UPDATE',
                   'REJECTED',
                   'PENDING',
                   'APPROVED'
    )
  AND (
    (v_role = 'TEAM_LEADER')
        OR v_role = 'MANAGER'
    );

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_employee_view_detail"("p_user_id" int8, "p_employee_id" int8)
  RETURNS "pg_catalog"."jsonb" AS $BODY$
DECLARE
v_user_exists BOOLEAN;
    v_result jsonb;
BEGIN
    /* =============================
       1. CHECK USER
       ============================= */
SELECT EXISTS (
    SELECT 1
    FROM tbl_user
    WHERE id = p_user_id
      AND status = 'ACTIVE'
)
INTO v_user_exists;

IF NOT v_user_exists THEN
        RETURN NULL;
END IF;

    /* =============================
       2. BUILD JSON RESULT
       ============================= */
SELECT jsonb_build_object(
               'employee',
               (
                   SELECT to_jsonb(e)
                   FROM tbl_employee e
                   WHERE e.id = p_employee_id
               ),
               'certificates',
               COALESCE(
                       (
                           SELECT jsonb_agg(to_jsonb(c))
                           FROM employee_certificates c
                           WHERE c.employee_id = p_employee_id
                       ),
                       '[]'::jsonb
               ),
               'family_relations',
               COALESCE(
                       (
                           SELECT jsonb_agg(to_jsonb(f))
                           FROM employee_family_relations f
                           WHERE f.employee_id = p_employee_id
                       ),
                       '[]'::jsonb
               )
       )
INTO v_result;

/* =============================
   3. NOT FOUND EMPLOYEE
   ============================= */
IF v_result -> 'employee' IS NULL THEN
        RETURN NULL;
END IF;

RETURN v_result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION "public"."fn_form_employee_registration_detail"("p_user_id" int8, "p_form_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "resume" text, "cv_url" varchar, "note" text, "job_position" varchar, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    f.id,
    f.employee_id,
    f.resume,
    f.cv_url,
    f.note,
    f.job_position,
    f.receiver_id,
    f.status,
    f.submit_date,
    f.approve_date,
    f.leader_note,
    f.created_by,
    f.created_at,
    f.updated_by,
    f.updated_at
FROM form_employee_registration f
WHERE f.id = p_form_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_form_promotion_detail"("p_user_id" int8, "p_form_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "old_position" varchar, "new_position" varchar, "reason" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    f.id,
    f.employee_id,
    f.old_position,
    f.new_position,
    f.reason,
    f.receiver_id,
    f.status,
    f.submit_date,
    f.approve_date,
    f.leader_note,
    f.created_by,
    f.created_at,
    f.updated_by,
    f.updated_at
FROM form_promotion f
WHERE f.id = p_form_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_form_proposal_detail"("p_user_id" int8, "p_form_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "content" text, "detail" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    f.id,
    f.employee_id,
    f.content,
    f.detail,
    f.receiver_id,
    f.status,
    f.submit_date,
    f.approve_date,
    f.leader_note,
    f.created_by,
    f.created_at,
    f.updated_by,
    f.updated_at
FROM form_proposal f
WHERE f.id = p_form_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_form_salary_increase_detail"("p_user_id" int8, "p_form_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "times" int4, "old_level" varchar, "new_level" varchar, "reason" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_by" int8, "created_at" timestamp, "updated_by" int8, "updated_at" timestamp) AS $BODY$
SELECT
    f.id,
    f.employee_id,
    f.times,
    f.old_level,
    f.new_level,
    f.reason,
    f.receiver_id,
    f.status,
    f.submit_date,
    f.approve_date,
    f.leader_note,
    f.created_by,
    f.created_at,
    f.updated_by,
    f.updated_at
FROM form_salary_increase f
WHERE f.id = p_form_id;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_get_employee_registration_by_employee"("p_employee_id" int8)
  RETURNS "pg_catalog"."jsonb" AS $BODY$
BEGIN
RETURN (
    SELECT to_jsonb(f)
    FROM form_employee_registration f
    WHERE f.employee_id = p_employee_id
    ORDER BY f.created_at DESC
    LIMIT 1
    );
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION "public"."fn_get_form_promotion_by_employee"("p_employee_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "old_position" varchar, "new_position" varchar, "reason" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_at" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    fp.id,
    fp.employee_id,
    fp.old_position,
    fp.new_position,
    fp.reason,
    fp.receiver_id,
    fp.status,
    fp.submit_date,
    fp.approve_date,
    fp.leader_note,
    fp.created_at
FROM form_promotion fp
         JOIN tbl_employee e ON e.id = fp.employee_id
WHERE fp.employee_id = p_employee_id
  AND e.status = 'APPROVED'
ORDER BY fp.created_at DESC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_get_form_proposal_by_employee"("p_employee_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "content" text, "detail" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_at" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    fp.id,
    fp.employee_id,
    fp.content,
    fp.detail,
    fp.receiver_id,
    fp.status,
    fp.submit_date,
    fp.approve_date,
    fp.leader_note,
    fp.created_at
FROM form_proposal fp
         JOIN tbl_employee e ON e.id = fp.employee_id
WHERE fp.employee_id = p_employee_id
  AND e.status = 'APPROVED'
ORDER BY fp.created_at DESC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_get_form_salary_increase_by_employee"("p_employee_id" int8)
  RETURNS TABLE("id" int8, "employee_id" int8, "times" int4, "old_level" varchar, "new_level" varchar, "reason" text, "receiver_id" int8, "status" varchar, "submit_date" timestamp, "approve_date" timestamp, "leader_note" text, "created_at" timestamp) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    fsi.id,
    fsi.employee_id,
    fsi.times,
    fsi.old_level,
    fsi.new_level,
    fsi.reason,
    fsi.receiver_id,
    fsi.status,
    fsi.submit_date,
    fsi.approve_date,
    fsi.leader_note,
    fsi.created_at
FROM form_salary_increase fsi
         JOIN tbl_employee e ON e.id = fsi.employee_id
WHERE fsi.employee_id = p_employee_id
  AND e.status = 'APPROVED'
ORDER BY fsi.created_at DESC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_contract_termination"()
  RETURNS TABLE("form_id" int8, "employee_id" int8, "employee_code" varchar, "full_name" varchar, "termination_date" date, "status" varchar, "submit_date" date, "archive_number" varchar) AS $BODY$
BEGIN
RETURN QUERY
SELECT
    f.id,
    e.id,
    e.employee_code,
    e.full_name,
    f.termination_date,
    f.status,
    f.submit_date::date,
        e.archive_number
FROM form_contract_termination f
         JOIN tbl_employee e ON e.id = f.employee_id
ORDER BY f.created_at DESC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_employee_registration_pending"("p_user_id" int8)
  RETURNS TABLE("form_id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "job_position" varchar, "receiver_id" int8, "form_status" varchar, "submit_date" timestamp, "created_at" timestamp) AS $BODY$
DECLARE
v_role varchar;
BEGIN
    -- 1. Lấy role user
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id
  AND status = 'ACTIVE';

-- 2. Check quyền
IF v_role NOT IN ('TEAM_LEADER', 'MANAGER') THEN
        RAISE EXCEPTION 'PERMISSION_DENIED';
END IF;

    -- 3. Trả danh sách pending
RETURN QUERY
SELECT
    f.id              AS form_id,
    f.employee_id,
    e.employee_code,
    e.full_name       AS employee_name,
    f.job_position,
    f.receiver_id,
    f.status          AS form_status,
    f.submit_date,
    f.created_at
FROM form_employee_registration f
         JOIN tbl_employee e
              ON e.id = f.employee_id
WHERE f.status = 'PENDING'
ORDER BY f.submit_date DESC;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_get_user_managers"()
  RETURNS TABLE("id" int8, "username" varchar, "fullname" varchar, "role" varchar, "status" varchar, "created_at" timestamp, "team" varchar) AS $BODY$
SELECT
    u.id,
    u.username,
    u.fullname,
    u.role,
    u.status,
    u.created_at,
    u.team
FROM tbl_user u
WHERE u.role IN ('MANAGER')
  AND u.status = 'ACTIVE'
ORDER BY u.created_at DESC;
$BODY$
LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_promotion_pending"("p_user_id" int8)
  RETURNS TABLE("form_id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "old_position" varchar, "new_position" varchar, "receiver_id" int8, "form_status" varchar, "submit_date" timestamp, "created_at" timestamp) AS $BODY$
DECLARE
v_role varchar;
BEGIN
    -- Lấy role user
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id
  AND status = 'ACTIVE';

-- Check quyền
IF v_role NOT IN ('TEAM_LEADER', 'MANAGER') THEN
        RAISE EXCEPTION 'PERMISSION_DENIED';
END IF;

    -- Trả danh sách pending promotion
RETURN QUERY
SELECT
    f.id AS form_id,
    f.employee_id,
    e.employee_code,
    e.full_name AS employee_name,
    f.old_position,
    f.new_position,
    f.receiver_id,
    f.status AS form_status,
    f.submit_date,
    f.created_at
FROM form_promotion f
         JOIN tbl_employee e ON e.id = f.employee_id
WHERE f.status = 'PENDING'
ORDER BY f.submit_date DESC;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_proposal_pending"("p_user_id" int8)
  RETURNS TABLE("form_id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "content" text, "receiver_id" int8, "form_status" varchar, "submit_date" timestamp, "created_at" timestamp) AS $BODY$
DECLARE
v_role varchar;
BEGIN
    -- Lấy role user
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id
  AND status = 'ACTIVE';

-- Check quyền
IF v_role NOT IN ('TEAM_LEADER', 'MANAGER') THEN
        RAISE EXCEPTION 'PERMISSION_DENIED';
END IF;

    -- Trả danh sách pending proposal
RETURN QUERY
SELECT
    f.id AS form_id,
    f.employee_id,
    e.employee_code,
    e.full_name AS employee_name,
    f.content,
    f.receiver_id,
    f.status AS form_status,
    f.submit_date,
    f.created_at
FROM form_proposal f
         JOIN tbl_employee e ON e.id = f.employee_id
WHERE f.status = 'PENDING'
ORDER BY f.submit_date DESC;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION "public"."fn_list_salary_increase_pending"("p_user_id" int8)
  RETURNS TABLE("form_id" int8, "employee_id" int8, "employee_code" varchar, "employee_name" varchar, "times" int4, "old_level" varchar, "new_level" varchar, "receiver_id" int8, "form_status" varchar, "submit_date" timestamp, "created_at" timestamp) AS $BODY$
DECLARE
v_role varchar;
BEGIN
    -- Lấy role user
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id
  AND status = 'ACTIVE';

-- Check quyền
IF v_role NOT IN ('TEAM_LEADER', 'MANAGER') THEN
        RAISE EXCEPTION 'PERMISSION_DENIED';
END IF;

    -- Trả danh sách pending salary increase
RETURN QUERY
SELECT
    f.id AS form_id,
    f.employee_id,
    e.employee_code,
    e.full_name AS employee_name,
    f.times,
    f.old_level,
    f.new_level,
    f.receiver_id,
    f.status AS form_status,
    f.submit_date,
    f.created_at
FROM form_salary_increase f
         JOIN tbl_employee e ON e.id = f.employee_id
WHERE f.status = 'PENDING'
ORDER BY f.submit_date DESC;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE PROCEDURE "public"."sp_add_employee"(IN "p_user_id" int8, IN "p_full_name" varchar, IN "p_gender" varchar, IN "p_date_of_birth" date, IN "p_address" varchar, IN "p_team" varchar, IN "p_avatar_url" varchar, IN "p_identity_number" varchar, IN "p_phone" varchar, IN "p_email" varchar, OUT "o_employee_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_employee_code varchar;
BEGIN
    -- ===============================
    -- 1. Tự sinh mã nhân viên
    -- ===============================
    v_employee_code := 'EMP' || LPAD(nextval('seq_employee_code')::text, 6, '0');

    -- ===============================
    -- 2. Insert nhân viên
    -- ===============================
INSERT INTO tbl_employee (
    employee_code,
    full_name,
    gender,
    date_of_birth,
    address,
    team,
    avatar_url,
    identity_number,
    phone,
    email,
    status,
    created_by
)
VALUES (
           v_employee_code,
           p_full_name,
           p_gender,
           p_date_of_birth,
           p_address,
           p_team,
           p_avatar_url,
           p_identity_number,
           p_phone,
           p_email,
           'DRAFT',
           p_user_id
       )
    RETURNING id
INTO o_employee_id;
o_success := TRUE;
    o_error_code := NULL;

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := SQLERRM;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_add_employee_certificates"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_certificates" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_cert JSON;
    v_certificates_json JSON;
BEGIN
    -- CHECK OWNER
    IF NOT EXISTS (
        SELECT 1 FROM tbl_employee
        WHERE id = p_employee_id
          AND created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    -- CAST TEXT -> JSON
BEGIN
        v_certificates_json := p_certificates::JSON;
EXCEPTION WHEN others THEN
        o_success := FALSE;
        o_error_code := 'INVALID_CERT_FORMAT';
        RETURN;
END;

FOR v_cert IN
SELECT * FROM json_array_elements(v_certificates_json)
                  LOOP
    INSERT INTO employee_certificates (
    employee_id, name, issue_date, content, field_url, created_by
)
VALUES (
    p_employee_id,
    v_cert->>'name',
    (v_cert->>'issue_date')::DATE,
    v_cert->>'content',
    v_cert->>'field_url',
    p_user_id
    );
END LOOP;

    o_success := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_add_employee_family"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_family_relations" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_item JSON;
    v_data JSON;
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM tbl_employee
        WHERE id = p_employee_id
          AND created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

BEGIN
        v_data := p_family_relations::JSON;
EXCEPTION WHEN others THEN
        o_success := FALSE;
        o_error_code := 'INVALID_FAMILY_FORMAT';
        RETURN;
END;

FOR v_item IN
SELECT * FROM json_array_elements(v_data)
                  LOOP
    INSERT INTO employee_family_relations (
    employee_id, full_name, gender,
    date_of_birth, identity_card_number,
    relationship, address, created_by
)
VALUES (
    p_employee_id,
    v_item->>'full_name',
    (v_item->>'gender')::INT,
    (v_item->>'date_of_birth')::DATE,
    v_item->>'identity_card_number',
    v_item->>'relationship',
    v_item->>'address',
    p_user_id
    );
END LOOP;

    o_success := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_check_employee_ready_for_registration"(IN "p_user_id" int8, IN "p_employee_id" int8, OUT "o_ready" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_cert_count INT;
    v_family_count INT;
    v_emp_status VARCHAR;
BEGIN
    /* =============================
       1. CHECK EMPLOYEE EXIST
       ============================= */
SELECT status
INTO v_emp_status
FROM tbl_employee
WHERE id = p_employee_id;

IF v_emp_status IS NULL THEN
        o_ready := FALSE;
        o_error_code := 'EMP_NOT_FOUND';
        RETURN;
END IF;

    /* =============================
       2. CHECK EMPLOYEE STATUS
       ============================= */
    IF v_emp_status NOT IN ('DRAFT', 'UPDATE') THEN
        o_ready := FALSE;
        o_error_code := 'EMP_STATUS_NOT_ALLOWED';
        RETURN;
END IF;

    /* =============================
       3. CHECK CERTIFICATE
       ============================= */
SELECT COUNT(*)
INTO v_cert_count
FROM employee_certificates
WHERE employee_id = p_employee_id;

IF v_cert_count = 0 THEN
        o_ready := FALSE;
        o_error_code := 'CERTIFICATE_REQUIRED';
        RETURN;
END IF;

    /* =============================
       4. CHECK FAMILY RELATION
       ============================= */
SELECT COUNT(*)
INTO v_family_count
FROM employee_family_relations
WHERE employee_id = p_employee_id;

IF v_family_count = 0 THEN
        o_ready := FALSE;
        o_error_code := 'FAMILY_RELATION_REQUIRED';
        RETURN;
END IF;

    /* =============================
       OK
       ============================= */
    o_ready := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_create_contract_termination"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_termination_date" date, IN "p_termination_reason" text, OUT "o_termination_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role varchar;
    v_emp_status varchar;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;
    o_termination_id := NULL;

    /* ===============================
       1. CHECK ROLE
       =============================== */
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id;

IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    /* ===============================
       2. CHECK EMPLOYEE APPROVED
       =============================== */
SELECT status
INTO v_emp_status
FROM tbl_employee
WHERE id = p_employee_id;

IF v_emp_status IS NULL THEN
        o_error_code := 'EMPLOYEE_NOT_FOUND';
        RETURN;
END IF;

    IF v_emp_status <> 'APPROVED' THEN
        o_error_code := 'EMPLOYEE_NOT_APPROVED';
        RETURN;
END IF;

    /* ===============================
       3. INSERT + RETURN ID
       =============================== */
INSERT INTO form_contract_termination (
    employee_id,
    termination_date,
    termination_reason,
    status,
    created_by
)
VALUES (
           p_employee_id,
           p_termination_date,
           p_termination_reason,
           'DRAFT',
           p_user_id
       )
    RETURNING id INTO o_termination_id;

o_success := TRUE;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_create_employee_registration"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_form_data" text, OUT "o_success" bool, OUT "o_error_code" varchar, OUT "o_form_id" int8)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_json JSONB;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;
    o_form_id := NULL;

    /* 1. CHECK USER */
SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND';
        RETURN;
END IF;

    IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    /* 2. CHECK EMPLOYEE */
    IF NOT EXISTS (
        SELECT 1 FROM tbl_employee
        WHERE id = p_employee_id
          AND created_by = p_user_id
          AND status = 'DRAFT'
    ) THEN
        o_error_code := 'EMP_NOT_VALID';
        RETURN;
END IF;

    /* 3. CHECK CERT + FAMILY */
    IF NOT EXISTS (
        SELECT 1 FROM employee_certificates
        WHERE employee_id = p_employee_id
    ) THEN
        o_error_code := 'CERTIFICATE_REQUIRED';
        RETURN;
END IF;

    IF NOT EXISTS (
        SELECT 1 FROM employee_family_relations
        WHERE employee_id = p_employee_id
    ) THEN
        o_error_code := 'FAMILY_REQUIRED';
        RETURN;
END IF;

    /* 4. PARSE JSON */
BEGIN
        v_json := p_form_data::JSONB;
EXCEPTION
        WHEN OTHERS THEN
            o_error_code := 'INVALID_JSON_FORMAT';
            RETURN;
END;

    /* 5. INSERT FORM + GET ID */
INSERT INTO form_employee_registration (
    employee_id,
    resume,
    cv_url,
    note,
    job_position,
    receiver_id,
    status,
    created_by,
    created_at
)
VALUES (
           p_employee_id,
           v_json->>'resume',
           v_json->>'cv_url',
           v_json->>'note',
           v_json->>'job_position',
           (v_json->>'receiver_id')::BIGINT,
           'DRAFT',
           p_user_id,
           NOW()
       )
    RETURNING id INTO o_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_create_form_promotion"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_old_position" varchar, IN "p_new_position" varchar, IN "p_reason" text, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar, OUT "o_form_id" int8)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_emp_status VARCHAR;
    v_emp_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;
    o_form_id := NULL;

    /* 1. CHECK USER */
SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND'; RETURN;
END IF;

    IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

    /* 2. CHECK EMPLOYEE */
SELECT status, created_by
INTO v_emp_status, v_emp_created_by
FROM tbl_employee
WHERE id = p_employee_id;

IF NOT FOUND THEN
        o_error_code := 'EMPLOYEE_NOT_FOUND'; RETURN;
END IF;

    IF v_emp_status <> 'APPROVED' THEN
        o_error_code := 'EMPLOYEE_NOT_APPROVED'; RETURN;
END IF;

    IF v_emp_created_by <> p_user_id THEN
        o_error_code := 'EMPLOYEE_NOT_OWNER'; RETURN;
END IF;

    /* 3. INSERT FORM + RETURN ID */
INSERT INTO form_promotion (
    employee_id,
    old_position,
    new_position,
    reason,
    receiver_id,
    status,
    created_by
)
VALUES (
           p_employee_id,
           p_old_position,
           p_new_position,
           p_reason,
           p_receiver_id,
           'DRAFT',
           p_user_id
       )
    RETURNING id INTO o_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := 'SYSTEM_ERROR';
        o_form_id := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_create_form_proposal"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_content" text, IN "p_detail" text, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar, OUT "o_form_id" int8)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_emp_status VARCHAR;
    v_emp_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;
    o_form_id := NULL;

    /* CHECK USER */
SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND'; RETURN;
END IF;

    IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

    /* CHECK EMPLOYEE */
SELECT status, created_by
INTO v_emp_status, v_emp_created_by
FROM tbl_employee
WHERE id = p_employee_id;

IF NOT FOUND THEN
        o_error_code := 'EMPLOYEE_NOT_FOUND'; RETURN;
END IF;

    IF v_emp_status <> 'APPROVED' THEN
        o_error_code := 'EMPLOYEE_NOT_APPROVED'; RETURN;
END IF;

    IF v_emp_created_by <> p_user_id THEN
        o_error_code := 'EMPLOYEE_NOT_OWNER'; RETURN;
END IF;

    /* INSERT FORM + RETURN ID */
INSERT INTO form_proposal (
    employee_id,
    content,
    detail,
    receiver_id,
    status,
    created_by
)
VALUES (
           p_employee_id,
           p_content,
           p_detail,
           p_receiver_id,
           'DRAFT',
           p_user_id
       )
    RETURNING id INTO o_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := 'SYSTEM_ERROR';
        o_form_id := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_create_form_salary_increase"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_times" int4, IN "p_old_level" varchar, IN "p_new_level" varchar, IN "p_reason" text, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar, OUT "o_form_id" int8)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_emp_status VARCHAR;
    v_emp_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;
    o_form_id := NULL;

    /* CHECK USER */
SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND'; RETURN;
END IF;

    IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

    /* CHECK EMPLOYEE */
SELECT status, created_by
INTO v_emp_status, v_emp_created_by
FROM tbl_employee
WHERE id = p_employee_id;

IF NOT FOUND THEN
        o_error_code := 'EMPLOYEE_NOT_FOUND'; RETURN;
END IF;

    IF v_emp_status <> 'APPROVED' THEN
        o_error_code := 'EMPLOYEE_NOT_APPROVED'; RETURN;
END IF;

    IF v_emp_created_by <> p_user_id THEN
        o_error_code := 'EMPLOYEE_NOT_OWNER'; RETURN;
END IF;

    /* INSERT FORM + RETURN ID */
INSERT INTO form_salary_increase (
    employee_id,
    times,
    old_level,
    new_level,
    reason,
    receiver_id,
    status,
    created_by
)
VALUES (
           p_employee_id,
           p_times,
           p_old_level,
           p_new_level,
           p_reason,
           p_receiver_id,
           'DRAFT',
           p_user_id
       )
    RETURNING id INTO o_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := 'SYSTEM_ERROR';
        o_form_id := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_employee_delete"(IN "p_user_id" int8, IN "p_employee_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
BEGIN
    -- ===== CHECK OWNER =====
    IF NOT EXISTS (
        SELECT 1
        FROM tbl_employee
        WHERE id = p_employee_id
          AND created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    -- ===== DELETE CHILD TABLES =====
DELETE FROM employee_certificates
WHERE employee_id = p_employee_id;

DELETE FROM employee_family_relations
WHERE employee_id = p_employee_id;

-- ===== DELETE EMPLOYEE =====
DELETE FROM tbl_employee
WHERE id = p_employee_id;

o_success := TRUE;
    o_error_code := NULL;

EXCEPTION WHEN others THEN
    o_success := FALSE;
    o_error_code := 'DELETE_EMPLOYEE_FAILED';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_leader_handle_contract_termination"(IN "p_leader_id" int8, IN "p_form_id" int8, IN "p_action" varchar, IN "p_note" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role          VARCHAR;
    v_employee_id   BIGINT;
    v_form_status   VARCHAR;
    v_emp_status    VARCHAR;
    v_term_date     DATE;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

    /* ===============================
       1. CHECK LEADER
       =============================== */
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_leader_id
  AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND';
        RETURN;
END IF;

    IF v_role <> 'MANAGER' THEN
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    /* ===============================
       2. CHECK FORM
       =============================== */
SELECT employee_id, termination_date
INTO v_employee_id, v_term_date
FROM form_contract_termination
WHERE id = p_form_id
  AND status = 'PENDING'
  AND receiver_id = p_leader_id;

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_WAIT_APPROVE';
        RETURN;
END IF;

    /* ===============================
       3. MAP ACTION
       =============================== */
    IF p_action = 'APPROVED' THEN
        v_form_status := 'TERMINATED';
        v_emp_status  := 'TERMINATED';

    ELSIF p_action = 'UPDATE' THEN
        v_form_status := 'REQUEST_MORE';
        v_emp_status  := NULL;

    ELSIF p_action = 'REJECTED' THEN
        v_form_status := 'REJECTED';
        v_emp_status  := NULL;

ELSE
        o_error_code := 'INVALID_ACTION';
        RETURN;
END IF;

    /* ===============================
       4. UPDATE FORM
       =============================== */
UPDATE form_contract_termination
SET
    status        = v_form_status,
    leader_note   = p_note,
    approve_date = CASE WHEN p_action = 'APPROVE' THEN NOW() ELSE NULL END,
    updated_by   = p_leader_id,
    updated_at   = NOW()
WHERE id = p_form_id;

/* ===============================
   5. UPDATE EMPLOYEE (ONLY APPROVE)
   =============================== */
IF p_action = 'APPROVED' THEN
UPDATE tbl_employee
SET
    status            = v_emp_status,
    terminated_at     = v_term_date,
    termination_reason = (
        SELECT termination_reason
        FROM form_contract_termination
        WHERE id = p_form_id
    ),
    status_updated_at = NOW(),
    updated_by        = p_leader_id,
    updated_at        = NOW()
WHERE id = v_employee_id;
END IF;

    o_success := TRUE;
    o_error_code := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := 'SYSTEM_ERROR';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_process_employee_registration"(IN "p_leader_id" int8, IN "p_form_id" int8, IN "p_action" varchar, IN "p_note" text, IN "p_action_date" timestamp, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role        VARCHAR;
    v_employee_id BIGINT;
    v_form_status VARCHAR;
    v_emp_status  VARCHAR;
    v_action_date TIMESTAMP;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

    /* ===============================
       1. CHECK LEADER
       =============================== */
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_leader_id
  AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND';
        RETURN;
END IF;

    IF v_role <> 'MANAGER' THEN
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    /* ===============================
       2. CHECK FORM
       =============================== */
SELECT employee_id
INTO v_employee_id
FROM form_employee_registration
WHERE id = p_form_id
  AND status = 'PENDING';

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_PENDING';
        RETURN;
END IF;

    /* ===============================
       3. MAP ACTION → STATUS
       =============================== */
    IF p_action = 'APPROVED' THEN
        v_form_status := 'APPROVED';
        v_emp_status  := 'APPROVED';

    ELSIF p_action = 'UPDATE' THEN
        v_form_status := 'UPDATE';
        v_emp_status  := 'UPDATE';

    ELSIF p_action = 'REJECT' THEN
        v_form_status := 'REJECTED';
        v_emp_status  := 'REJECTED';

ELSE
        o_error_code := 'INVALID_ACTION';
        RETURN;
END IF;

    /* ===============================
       4. NORMALIZE ACTION DATE
       =============================== */
    IF p_action = 'APPROVED' THEN
        IF p_action_date IS NULL THEN
            v_action_date := NOW();
ELSE
            -- nếu không có giờ thì mặc định 00:00:00
            v_action_date := date_trunc('second', p_action_date);
END IF;
ELSE
        v_action_date := NULL;
END IF;

    /* ===============================
       5. UPDATE FORM
       =============================== */
UPDATE form_employee_registration
SET status        = v_form_status,
    leader_note   = p_note,
    approve_date = v_action_date,
    updated_by   = p_leader_id,
    updated_at   = NOW()
WHERE id = p_form_id;

/* ===============================
   6. UPDATE EMPLOYEE
   =============================== */
UPDATE tbl_employee
SET status      = v_emp_status,
    updated_by = p_leader_id,
    updated_at = NOW()
WHERE id = v_employee_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        o_success := FALSE;
        o_error_code := 'SYSTEM_ERROR';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_process_form_promotion"(IN "p_manager_id" int8, IN "p_form_id" int8, IN "p_action" varchar, IN "p_note" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_manager_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'MANAGER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

UPDATE form_promotion
SET status = p_action,
    leader_note = p_note,
    approve_date = CASE WHEN p_action = 'APPROVED' THEN NOW() ELSE NULL END,
    updated_by = p_manager_id,
    updated_at = NOW()
WHERE id = p_form_id
  AND status = 'PENDING';

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_PENDING'; RETURN;
END IF;

    o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_process_form_proposal"(IN "p_manager_id" int8, IN "p_form_id" int8, IN "p_action" varchar, IN "p_note" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_manager_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'MANAGER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

UPDATE form_proposal
SET status = p_action,
    leader_note = p_note,
    approve_date = CASE WHEN p_action = 'APPROVED' THEN NOW() ELSE NULL END,
    updated_by = p_manager_id,
    updated_at = NOW()
WHERE id = p_form_id
  AND status = 'PENDING';

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_PENDING'; RETURN;
END IF;

    o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_process_form_salary_increase"(IN "p_manager_id" int8, IN "p_form_id" int8, IN "p_action" varchar, IN "p_note" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_manager_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'MANAGER' THEN
        o_error_code := 'PERMISSION_DENIED'; RETURN;
END IF;

UPDATE form_salary_increase
SET status = p_action,
    leader_note = p_note,
    approve_date = CASE WHEN p_action = 'APPROVED' THEN NOW() ELSE NULL END,
    updated_by = p_manager_id,
    updated_at = NOW()
WHERE id = p_form_id
  AND status = 'PENDING';

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_PENDING'; RETURN;
END IF;

    o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_submit_contract_termination"(IN "p_user_id" int8, IN "p_form_id" int8, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

    /* ===============================
       1. CHECK USER
       =============================== */
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id
  AND status = 'ACTIVE';

IF NOT FOUND THEN
        o_error_code := 'USER_NOT_FOUND';
        RETURN;
END IF;

    IF v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    /* ===============================
       2. UPDATE FORM
       =============================== */
UPDATE form_contract_termination
SET
    status      = 'PENDING',
    receiver_id = p_receiver_id,
    submit_date = NOW(),
    updated_by  = p_user_id,
    updated_at  = NOW()
WHERE id = p_form_id
  AND status IN ('DRAFT', 'UPDATE');

IF NOT FOUND THEN
        o_error_code := 'INVALID_STATUS';
        RETURN;
END IF;

    o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_submit_employee_registration"(IN "p_user_id" int8, IN "p_form_id" int8, IN "p_note" text, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_employee_id int8;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

    /* 1. Check form có được submit không + lấy employee_id */
SELECT employee_id
INTO v_employee_id
FROM form_employee_registration
WHERE id = p_form_id
  AND status IN ('DRAFT', 'UPDATE');

IF v_employee_id IS NULL THEN
        o_error_code := 'FORM_NOT_EDITABLE';
        RETURN;
END IF;

    /* 2. Update form -> PENDING */
UPDATE form_employee_registration
SET
    note         = p_note,
    receiver_id  = p_receiver_id,
    status       = 'PENDING',
    submit_date  = NOW(),
    updated_by   = p_user_id,
    updated_at   = NOW()
WHERE id = p_form_id;

/* 3. Update employee -> PENDING */
UPDATE tbl_employee
SET
    status     = 'PENDING',
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = v_employee_id;

o_success := TRUE;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_submit_form_promotion"(IN "p_user_id" int8, IN "p_form_id" int8, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_status VARCHAR;
    v_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'SUBMIT_FORBIDDEN'; RETURN;
END IF;

SELECT status, created_by
INTO v_status, v_created_by
FROM form_promotion
WHERE id = p_form_id;

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_FOUND'; RETURN;
END IF;

    IF v_created_by <> p_user_id THEN
        o_error_code := 'NOT_CREATOR'; RETURN;
END IF;

    IF v_status NOT IN ('DRAFT', 'UPDATE') THEN
        o_error_code := 'INVALID_STATUS'; RETURN;
END IF;

UPDATE form_promotion
SET status = 'PENDING',
    submit_date = NOW(),
    receiver_id = p_receiver_id,
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_submit_form_proposal"(IN "p_user_id" int8, IN "p_form_id" int8, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_status VARCHAR;
    v_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'SUBMIT_FORBIDDEN'; RETURN;
END IF;

SELECT status, created_by
INTO v_status, v_created_by
FROM form_proposal
WHERE id = p_form_id;

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_FOUND'; RETURN;
END IF;

    IF v_created_by <> p_user_id THEN
        o_error_code := 'NOT_CREATOR'; RETURN;
END IF;

    IF v_status NOT IN ('DRAFT', 'UPDATE') THEN
        o_error_code := 'INVALID_STATUS'; RETURN;
END IF;

UPDATE form_proposal
SET status = 'PENDING',
    submit_date = NOW(),
    receiver_id = p_receiver_id,
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_submit_form_salary_increase"(IN "p_user_id" int8, IN "p_form_id" int8, IN "p_receiver_id" int8, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_role VARCHAR;
    v_status VARCHAR;
    v_created_by BIGINT;
BEGIN
    o_success := FALSE;
    o_error_code := NULL;

SELECT role INTO v_role
FROM tbl_user
WHERE id = p_user_id AND status = 'ACTIVE';

IF NOT FOUND OR v_role <> 'TEAM_LEADER' THEN
        o_error_code := 'SUBMIT_FORBIDDEN'; RETURN;
END IF;

SELECT status, created_by
INTO v_status, v_created_by
FROM form_salary_increase
WHERE id = p_form_id;

IF NOT FOUND THEN
        o_error_code := 'FORM_NOT_FOUND'; RETURN;
END IF;

    IF v_created_by <> p_user_id THEN
        o_error_code := 'NOT_CREATOR'; RETURN;
END IF;

    IF v_status NOT IN ('DRAFT', 'UPDATE') THEN
        o_error_code := 'INVALID_STATUS'; RETURN;
END IF;

UPDATE form_salary_increase
SET status = 'PENDING',
    submit_date = NOW(),
    receiver_id = p_receiver_id,
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_form_id;

o_success := TRUE;
    o_error_code := 'SUCCESS';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_employee_certificate"(IN "p_user_id" int8, IN "p_cert_id" int8, IN "p_cert_data" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_data JSON;
BEGIN
    -- CHECK OWNER
    IF NOT EXISTS (
        SELECT 1
        FROM employee_certificates ec
        JOIN tbl_employee e ON e.id = ec.employee_id
        WHERE ec.id = p_cert_id
          AND e.created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

    -- CAST
BEGIN
        v_data := p_cert_data::JSON;
EXCEPTION WHEN others THEN
        o_success := FALSE;
        o_error_code := 'INVALID_CERT_FORMAT';
        RETURN;
END;

UPDATE employee_certificates
SET
    name       = v_data->>'name',
    issue_date = (v_data->>'issue_date')::DATE,
    content    = v_data->>'content',
    field_url  = v_data->>'field_url',
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_cert_id;

o_success := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_employee_family"(IN "p_user_id" int8, IN "p_family_id" int8, IN "p_family_data" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_data JSON;
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM employee_family_relations f
        JOIN tbl_employee e ON e.id = f.employee_id
        WHERE f.id = p_family_id
          AND e.created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

BEGIN
        v_data := p_family_data::JSON;
EXCEPTION WHEN others THEN
        o_success := FALSE;
        o_error_code := 'INVALID_FAMILY_FORMAT';
        RETURN;
END;

UPDATE employee_family_relations
SET
    full_name = v_data->>'full_name',
    gender = (v_data->>'gender')::INT,
    date_of_birth = (v_data->>'date_of_birth')::DATE,
    identity_card_number = v_data->>'identity_card_number',
    relationship = v_data->>'relationship',
    address = v_data->>'address',
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_family_id;

o_success := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_employee_info"(IN "p_user_id" int8, IN "p_employee_id" int8, IN "p_employee_data" text, OUT "o_success" bool, OUT "o_error_code" varchar)
 AS $BODY$
DECLARE
v_data JSON;
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM tbl_employee
        WHERE id = p_employee_id
          AND created_by = p_user_id
    ) THEN
        o_success := FALSE;
        o_error_code := 'PERMISSION_DENIED';
        RETURN;
END IF;

BEGIN
        v_data := p_employee_data::JSON;
EXCEPTION WHEN others THEN
        o_success := FALSE;
        o_error_code := 'INVALID_EMPLOYEE_FORMAT';
        RETURN;
END;

UPDATE tbl_employee
SET
    full_name = v_data->>'fullName',
    gender = v_data->>'gender',
    date_of_birth = (v_data->>'dateOfBirth')::DATE,
    address = v_data->>'address',
    team = v_data->>'team',
    avatar_url = v_data->>'avatar_url',
    phone = v_data->>'phone',
    email = v_data->>'email',
    updated_by = p_user_id,
    updated_at = NOW()
WHERE id = p_employee_id;

o_success := TRUE;
    o_error_code := NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_employee_registration"(IN "p_id" int8, IN "p_resume" text, IN "p_cv_url" varchar, IN "p_note" text, IN "p_job_position" varchar, IN "p_user_id" int8, OUT "o_success" bool, OUT "o_error" varchar)
 AS $BODY$
DECLARE
v_exists int;
    v_role   varchar;
BEGIN
    o_success := false;
    o_error   := null;

    -- Check role user
SELECT role
INTO v_role
FROM tbl_user
WHERE id = p_user_id;

IF v_role IS NULL OR v_role <> 'TEAM_LEADER' THEN
        o_error := 'PERMISSION_DENIED';
        RETURN;
END IF;

    -- Check tồn tại bản ghi
SELECT COUNT(*) INTO v_exists
FROM form_employee_registration
WHERE id = p_id;

IF v_exists = 0 THEN
        o_error := 'NOT_FOUND';
        RETURN;
END IF;

UPDATE form_employee_registration
SET
    resume        = p_resume,
    cv_url        = p_cv_url,
    note          = p_note,
    job_position  = p_job_position,
    updated_by    = p_user_id,
    updated_at    = NOW()
WHERE id = p_id;

o_success := true;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_form_promotion"(IN "p_id" int8, IN "p_old_position" varchar, IN "p_new_position" varchar, IN "p_reason" text, IN "p_receiver_id" int8, IN "p_status" varchar, IN "p_leader_note" text, IN "p_updated_by" int8, OUT "o_success" bool, OUT "o_message" text)
 AS $BODY$
BEGIN
UPDATE form_promotion
SET
    old_position = p_old_position,
    new_position = p_new_position,
    reason = p_reason,
    receiver_id = p_receiver_id,
    status = p_status,
    leader_note = p_leader_note,
    updated_by = p_updated_by,
    updated_at = CURRENT_TIMESTAMP
WHERE id = p_id;

IF FOUND THEN
        o_success := TRUE;
        o_message := 'UPDATE_FORM_PROMOTION_SUCCESS';
ELSE
        o_success := FALSE;
        o_message := 'FORM_PROMOTION_NOT_FOUND';
END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_form_proposal"(IN "p_id" int8, IN "p_content" text, IN "p_detail" text, IN "p_receiver_id" int8, IN "p_status" varchar, IN "p_leader_note" text, IN "p_updated_by" int8, OUT "o_success" bool, OUT "o_message" text)
 AS $BODY$
BEGIN
UPDATE form_proposal
SET
    content = p_content,
    detail = p_detail,
    receiver_id = p_receiver_id,
    status = p_status,
    leader_note = p_leader_note,
    updated_by = p_updated_by,
    updated_at = CURRENT_TIMESTAMP
WHERE id = p_id;

IF FOUND THEN
        o_success := TRUE;
        o_message := 'UPDATE_FORM_PROPOSAL_SUCCESS';
ELSE
        o_success := FALSE;
        o_message := 'FORM_PROPOSAL_NOT_FOUND';
END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE "public"."sp_update_form_salary_increase"(IN "p_id" int8, IN "p_times" int4, IN "p_old_level" varchar, IN "p_new_level" varchar, IN "p_reason" text, IN "p_receiver_id" int8, IN "p_status" varchar, IN "p_leader_note" text, IN "p_updated_by" int8, OUT "o_success" bool, OUT "o_message" text)
 AS $BODY$
BEGIN
UPDATE form_salary_increase
SET
    times = p_times,
    old_level = p_old_level,
    new_level = p_new_level,
    reason = p_reason,
    receiver_id = p_receiver_id,
    status = p_status,
    leader_note = p_leader_note,
    updated_by = p_updated_by,
    updated_at = CURRENT_TIMESTAMP
WHERE id = p_id;

IF FOUND THEN
        o_success := TRUE;
        o_message := 'UPDATE_FORM_SALARY_INCREASE_SUCCESS';
ELSE
        o_success := FALSE;
        o_message := 'FORM_SALARY_INCREASE_NOT_FOUND';
END IF;
END;
$BODY$
LANGUAGE plpgsql;
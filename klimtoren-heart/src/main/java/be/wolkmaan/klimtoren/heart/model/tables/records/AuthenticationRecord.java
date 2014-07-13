/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthenticationRecord extends org.jooq.impl.UpdatableRecordImpl<be.wolkmaan.klimtoren.heart.model.tables.records.AuthenticationRecord> implements org.jooq.Record9<java.lang.Integer, java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean> {

	private static final long serialVersionUID = 983687208;

	/**
	 * Setter for <code>public.authentication.id</code>.
	 */
	public AuthenticationRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.authentication.username</code>.
	 */
	public AuthenticationRecord setUsername(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.username</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 255)
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>public.authentication.password</code>.
	 */
	public AuthenticationRecord setPassword(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.password</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.String getPassword() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>public.authentication.lastlogin_datetime</code>.
	 */
	public AuthenticationRecord setLastloginDatetime(java.time.LocalDateTime value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.lastlogin_datetime</code>.
	 */
	public java.time.LocalDateTime getLastloginDatetime() {
		return (java.time.LocalDateTime) getValue(3);
	}

	/**
	 * Setter for <code>public.authentication.lastloginattemptfailure_datetime</code>.
	 */
	public AuthenticationRecord setLastloginattemptfailureDatetime(java.time.LocalDateTime value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.lastloginattemptfailure_datetime</code>.
	 */
	public java.time.LocalDateTime getLastloginattemptfailureDatetime() {
		return (java.time.LocalDateTime) getValue(4);
	}

	/**
	 * Setter for <code>public.authentication.loginattemptfailurecount</code>.
	 */
	public AuthenticationRecord setLoginattemptfailurecount(java.lang.Integer value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.loginattemptfailurecount</code>.
	 */
	public java.lang.Integer getLoginattemptfailurecount() {
		return (java.lang.Integer) getValue(5);
	}

	/**
	 * Setter for <code>public.authentication.isgranted</code>.
	 */
	public AuthenticationRecord setIsgranted(java.lang.Boolean value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.isgranted</code>.
	 */
	public java.lang.Boolean getIsgranted() {
		return (java.lang.Boolean) getValue(6);
	}

	/**
	 * Setter for <code>public.authentication.islocked</code>.
	 */
	public AuthenticationRecord setIslocked(java.lang.Boolean value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.islocked</code>.
	 */
	public java.lang.Boolean getIslocked() {
		return (java.lang.Boolean) getValue(7);
	}

	/**
	 * Setter for <code>public.authentication.enableautounlock</code>.
	 */
	public AuthenticationRecord setEnableautounlock(java.lang.Boolean value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>public.authentication.enableautounlock</code>.
	 */
	public java.lang.Boolean getEnableautounlock() {
		return (java.lang.Boolean) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field4() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.LASTLOGIN_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field5() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.LASTLOGINATTEMPTFAILURE_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.LOGINATTEMPTFAILURECOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field7() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.ISGRANTED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field8() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.ISLOCKED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field9() {
		return be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION.ENABLEAUTOUNLOCK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.time.LocalDateTime value4() {
		return getLastloginDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.time.LocalDateTime value5() {
		return getLastloginattemptfailureDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getLoginattemptfailurecount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value7() {
		return getIsgranted();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value8() {
		return getIslocked();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value9() {
		return getEnableautounlock();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value2(java.lang.String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value3(java.lang.String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value4(java.time.LocalDateTime value) {
		setLastloginDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value5(java.time.LocalDateTime value) {
		setLastloginattemptfailureDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value6(java.lang.Integer value) {
		setLoginattemptfailurecount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value7(java.lang.Boolean value) {
		setIsgranted(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value8(java.lang.Boolean value) {
		setIslocked(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord value9(java.lang.Boolean value) {
		setEnableautounlock(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.time.LocalDateTime value4, java.time.LocalDateTime value5, java.lang.Integer value6, java.lang.Boolean value7, java.lang.Boolean value8, java.lang.Boolean value9) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AuthenticationRecord
	 */
	public AuthenticationRecord() {
		super(be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION);
	}

	/**
	 * Create a detached, initialised AuthenticationRecord
	 */
	public AuthenticationRecord(java.lang.Integer id, java.lang.String username, java.lang.String password, java.time.LocalDateTime lastloginDatetime, java.time.LocalDateTime lastloginattemptfailureDatetime, java.lang.Integer loginattemptfailurecount, java.lang.Boolean isgranted, java.lang.Boolean islocked, java.lang.Boolean enableautounlock) {
		super(be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION);

		setValue(0, id);
		setValue(1, username);
		setValue(2, password);
		setValue(3, lastloginDatetime);
		setValue(4, lastloginattemptfailureDatetime);
		setValue(5, loginattemptfailurecount);
		setValue(6, isgranted);
		setValue(7, islocked);
		setValue(8, enableautounlock);
	}
}
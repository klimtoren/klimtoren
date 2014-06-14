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
public class P2pRelationsRecord extends org.jooq.impl.UpdatableRecordImpl<be.wolkmaan.klimtoren.heart.model.tables.records.P2pRelationsRecord> implements org.jooq.Record6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Integer, be.wolkmaan.klimtoren.heart.enums.Kind> {

	private static final long serialVersionUID = 2067257474;

	/**
	 * Setter for <code>public.p2p_relations.id</code>.
	 */
	public P2pRelationsRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.p2p_relations.start_datetime</code>.
	 */
	public P2pRelationsRecord setStartDatetime(java.time.LocalDateTime value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.start_datetime</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.time.LocalDateTime getStartDatetime() {
		return (java.time.LocalDateTime) getValue(1);
	}

	/**
	 * Setter for <code>public.p2p_relations.end_datetime</code>.
	 */
	public P2pRelationsRecord setEndDatetime(java.time.LocalDateTime value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.end_datetime</code>.
	 */
	public java.time.LocalDateTime getEndDatetime() {
		return (java.time.LocalDateTime) getValue(2);
	}

	/**
	 * Setter for <code>public.p2p_relations.context</code>.
	 */
	public P2pRelationsRecord setContext(java.lang.Integer value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.context</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getContext() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>public.p2p_relations.reference</code>.
	 */
	public P2pRelationsRecord setReference(java.lang.Integer value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.reference</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getReference() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>public.p2p_relations.kind</code>.
	 */
	public P2pRelationsRecord setKind(be.wolkmaan.klimtoren.heart.enums.Kind value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.p2p_relations.kind</code>.
	 */
	@javax.validation.constraints.NotNull
	public be.wolkmaan.klimtoren.heart.enums.Kind getKind() {
		return (be.wolkmaan.klimtoren.heart.enums.Kind) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Integer, be.wolkmaan.klimtoren.heart.enums.Kind> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, java.lang.Integer, java.lang.Integer, be.wolkmaan.klimtoren.heart.enums.Kind> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field2() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.START_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field3() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.END_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.CONTEXT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.REFERENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<be.wolkmaan.klimtoren.heart.enums.Kind> field6() {
		return be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS.KIND;
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
	public java.time.LocalDateTime value2() {
		return getStartDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.time.LocalDateTime value3() {
		return getEndDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getContext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.enums.Kind value6() {
		return getKind();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value2(java.time.LocalDateTime value) {
		setStartDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value3(java.time.LocalDateTime value) {
		setEndDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value4(java.lang.Integer value) {
		setContext(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value5(java.lang.Integer value) {
		setReference(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord value6(be.wolkmaan.klimtoren.heart.enums.Kind value) {
		setKind(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P2pRelationsRecord values(java.lang.Integer value1, java.time.LocalDateTime value2, java.time.LocalDateTime value3, java.lang.Integer value4, java.lang.Integer value5, be.wolkmaan.klimtoren.heart.enums.Kind value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached P2pRelationsRecord
	 */
	public P2pRelationsRecord() {
		super(be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS);
	}

	/**
	 * Create a detached, initialised P2pRelationsRecord
	 */
	public P2pRelationsRecord(java.lang.Integer id, java.time.LocalDateTime startDatetime, java.time.LocalDateTime endDatetime, java.lang.Integer context, java.lang.Integer reference, be.wolkmaan.klimtoren.heart.enums.Kind kind) {
		super(be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS);

		setValue(0, id);
		setValue(1, startDatetime);
		setValue(2, endDatetime);
		setValue(3, context);
		setValue(4, reference);
		setValue(5, kind);
	}
}

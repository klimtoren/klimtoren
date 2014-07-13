/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Eventlog extends org.jooq.impl.TableImpl<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord> {

	private static final long serialVersionUID = -1897518475;

	/**
	 * The singleton instance of <code>public.eventlog</code>
	 */
	public static final be.wolkmaan.klimtoren.heart.model.tables.Eventlog EVENTLOG = new be.wolkmaan.klimtoren.heart.model.tables.Eventlog();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord> getRecordType() {
		return be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord.class;
	}

	/**
	 * The column <code>public.eventlog.id</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.eventlog.title</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.eventlog.kind</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, be.wolkmaan.klimtoren.heart.enums.Kind> KIND = createField("kind", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "", new be.wolkmaan.klimtoren.heart.enums.KindConverter());

	/**
	 * The column <code>public.eventlog.message</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.String> MESSAGE = createField("message", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>public.eventlog.occured_datetime</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.time.LocalDateTime> OCCURED_DATETIME = createField("occured_datetime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new be.wolkmaan.klimtoren.heart.enums.LocalDateTimeConverter());

	/**
	 * The column <code>public.eventlog.forparty</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.Integer> FORPARTY = createField("forparty", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.eventlog.forresource</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.Integer> FORRESOURCE = createField("forresource", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>public.eventlog</code> table reference
	 */
	public Eventlog() {
		this("eventlog", null);
	}

	/**
	 * Create an aliased <code>public.eventlog</code> table reference
	 */
	public Eventlog(java.lang.String alias) {
		this(alias, be.wolkmaan.klimtoren.heart.model.tables.Eventlog.EVENTLOG);
	}

	private Eventlog(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord> aliased) {
		this(alias, aliased, null);
	}

	private Eventlog(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, be.wolkmaan.klimtoren.heart.model.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, java.lang.Long> getIdentity() {
		return be.wolkmaan.klimtoren.heart.model.Keys.IDENTITY_EVENTLOG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord> getPrimaryKey() {
		return be.wolkmaan.klimtoren.heart.model.Keys.EVENTLOG_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord>>asList(be.wolkmaan.klimtoren.heart.model.Keys.EVENTLOG_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord, ?>>asList(be.wolkmaan.klimtoren.heart.model.Keys.EVENTLOG__EVENTLOG_FORPARTY_FKEY, be.wolkmaan.klimtoren.heart.model.Keys.EVENTLOG__EVENTLOG_FORRESOURCE_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.model.tables.Eventlog as(java.lang.String alias) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Eventlog(alias, this);
	}

	/**
	 * Rename this table
	 */
	public be.wolkmaan.klimtoren.heart.model.tables.Eventlog rename(java.lang.String name) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Eventlog(name, null);
	}
}
/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 114863593;

	/**
	 * The singleton instance of <code>public</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("public");
	}

	@Override
	public final java.util.List<org.jooq.Sequence<?>> getSequences() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final java.util.List<org.jooq.Sequence<?>> getSequences0() {
		return java.util.Arrays.<org.jooq.Sequence<?>>asList(
			be.wolkmaan.klimtoren.heart.model.Sequences.COUNTRIES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.EVENTLOG_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.FULLNAMES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.LOCATIONS_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.P2P_RELATIONS_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.PARTIES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.PARTY_ATTRIBUTES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.PARTY_RESOURCES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.RESOURCE_ATTRIBUTES_ID_SEQ,
			be.wolkmaan.klimtoren.heart.model.Sequences.RESOURCES_ID_SEQ);
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION,
			be.wolkmaan.klimtoren.heart.model.tables.Countries.COUNTRIES,
			be.wolkmaan.klimtoren.heart.model.tables.Emails.EMAILS,
			be.wolkmaan.klimtoren.heart.model.tables.Eventlog.EVENTLOG,
			be.wolkmaan.klimtoren.heart.model.tables.Fullnames.FULLNAMES,
			be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS,
			be.wolkmaan.klimtoren.heart.model.tables.Locations.LOCATIONS,
			be.wolkmaan.klimtoren.heart.model.tables.Mailboxes.MAILBOXES,
			be.wolkmaan.klimtoren.heart.model.tables.Organizations.ORGANIZATIONS,
			be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS,
			be.wolkmaan.klimtoren.heart.model.tables.Parties.PARTIES,
			be.wolkmaan.klimtoren.heart.model.tables.PartyAttributes.PARTY_ATTRIBUTES,
			be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES,
			be.wolkmaan.klimtoren.heart.model.tables.Persons.PERSONS,
			be.wolkmaan.klimtoren.heart.model.tables.ResourceAttributes.RESOURCE_ATTRIBUTES,
			be.wolkmaan.klimtoren.heart.model.tables.Resources.RESOURCES,
			be.wolkmaan.klimtoren.heart.model.tables.Site.SITE,
			be.wolkmaan.klimtoren.heart.model.tables.Telephones.TELEPHONES);
	}
}
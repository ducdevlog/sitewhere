// Create default tenant if does not exist.
def tenant = tenantBuilder.getTenantByToken 'Smarthome'
if (tenant == null) {
	tenant = tenantBuilder.newTenant 'Smarthome', 'Default Tenant', 'sitewhere1234567890',
		'https://s3.amazonaws.com/sitewhere-demo/sitewhere-small.png', 'mongodb', 'construction'
	tenant.withAuthorizedUserId 'admin' 
	tenant.withAuthorizedUserId 'noadmin'
	tenant = tenantBuilder.persist tenant

	logger.info "[Create Tenant] ${tenant.id}"
}

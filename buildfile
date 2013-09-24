require 'buildr/git_auto_version'
require 'buildr/single_intermediate_layout'
require 'buildr/top_level_generate_dir'

desc "Example JEE Application"
define 'bootstrap-jsf' do
  project.group = 'org.realityforge.experiment.jee'
  compile.options.source = '1.7'
  compile.options.target = '1.7'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  compile.with :javax_javaee#, :javax_annotation

  test.using :testng

  package(:war)

  project.clean { rm_rf _(:artifacts) }

  iml.add_ejb_facet
  iml.add_jruby_facet

  ipr.add_exploded_war_artifact(project, :enable_jpa => true, :enable_ejb => true)
end

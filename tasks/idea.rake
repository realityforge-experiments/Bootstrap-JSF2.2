# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with this
# work for additional information regarding copyright ownership.  The ASF
# licenses this file to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
# License for the specific language governing permissions and limitations under
# the License.

raise "Patch applied in the lastest version of buildr" unless Buildr::VERSION == '1.4.12'

module Buildr #:nodoc:
  module IntellijIdea
    class IdeaModule
      def add_web_facet(options = {})
        name = options[:name] || "Web"
        url_base = options[:url_base] || "/"
        default_webroots = buildr_project.assets.paths
        webroots = options[:webroots] || default_webroots
        default_web_xml = "#{buildr_project._(:source, :main, :webapp)}/WEB-INF/web.xml"
        web_xml = options[:web_xml] || default_web_xml
        default_deployment_descriptors = ['glassfish-web.xml', 'context.xml'].
          collect { |d| "#{buildr_project._(:source, :main, :webapp)}/WEB-INF/#{d}" }
        deployment_descriptors = options[:deployment_descriptors] || default_deployment_descriptors

        add_facet(name, "web") do |f|
          f.configuration do |c|
            c.descriptors do |d|
              if File.exist?(web_xml) || options[:web_xml]
                d.deploymentDescriptor :name => 'web.xml', :url => file_path(web_xml)
              end
              deployment_descriptors.each do |deployment_descriptor|
                if File.exist?(deployment_descriptor) || options[:deployment_descriptors]
                  d.deploymentDescriptor :name => File.basename(deployment_descriptor), :url => file_path(deployment_descriptor)
                end
              end
            end
            c.webroots do |w|
              webroots.each do |webroot|
                w.root :url => file_path(webroot), :relative => url_base
              end
            end
          end
          # Patch Start
          default_enable_jsf = webroots.select{|webroot| File.exist?("#{webroot}/WEB-INF/faces-config.xml")}
          enable_jsf = options[:enable_jsf].nil? ? default_enable_jsf : options[:enable_jsf]
          f.facet(:type => 'jsf', :name => 'JSF') do |jsf|
            jsf.configuration
          end if enable_jsf
          # Patch End
        end
      end
    end
  end
end

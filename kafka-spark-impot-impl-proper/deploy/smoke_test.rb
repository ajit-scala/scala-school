#!/usr/bin/env ruby

require 'json'
require 'net/http'

def wait_until_200OK(url, timeout_in_minutes: 5)
  uri = URI(url)
  http = Net::HTTP.new(uri.host, uri.port)
  http.use_ssl = true
  stop = Time.now + timeout_in_minutes * 60
  while Time.now < stop
    puts "GET #{uri} "
    begin
      response = http.get(uri.path, {'Accept' => 'application/json'})
      puts response
      puts response.body
      return true if response.code == '200'
    rescue Exception => x
      puts x
    end
    sleep(2)
  end
  fail "#{uri} does not return status code 200 after #{timeout_in_minutes} minutes"
end

# This is the real integration test:
def run_smoke_tests(url)
  domains = %w(.com)
  domains.each do |domain|
    domain_url = url.gsub('.com', domain)
    wait_until_200OK("#{domain_url}/diagnostics/heartbeat")
  end
end

run_smoke_tests 'http://localhost:9000' if $0 ==__FILE__

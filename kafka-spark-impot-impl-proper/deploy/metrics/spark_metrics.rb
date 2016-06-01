require 'json'
require 'open-uri'
require 'date'

class Stats
  def heap_usage(data)
    data["gauges"]["driver.jvm.heap.usage"].round(2)
  end

  def last_delay(data)
    (data["gauges"]["driver.Categorization.StreamingMetrics.streaming.lastCompletedBatch_totalDelay"] / 1000.0).round(2)
  end

  def waiting_batches(data)
    data["gauges"]["driver.Categorization.StreamingMetrics.streaming.waitingBatches"]
  end

  def failed_stages(data)
    data["gauges"]["driver.DAGScheduler.stage.failedStages"]
  end

  # for some reason lastReceivedBatch_records is always 0 on AWS (cluster)
  def throughput(data)
    k = "driver.Categorization.StreamingMetrics.streaming"
    g = data["gauges"]
    if g["#{k}.lastReceivedBatch_records"] == 0 then
      0.0
    else
      ((g["#{k}.lastReceivedBatch_processingEndTime"] - g["#{k}.lastReceivedBatch_processingStartTime"]) / (g["#{k}.lastReceivedBatch_records"] / 1000.0)).round(2)
    end
  end

  def throughput_timers_p95(data)
    throughput_timers(data, 95)
  end

  def throughput_timers_p98(data)
    throughput_timers(data, 98)
  end

  def throughput_timers_p75(data)
    throughput_timers(data, 75)
  end

  private
  def throughput_timers(data, type)
    (1000 / data["timers"]["p#{type}"]).round(2)
  end

end

begin
  source = open('http://localhost:4040/metrics/json', :read_timeout => 10).read()

  json = JSON.parse(source)

  id_size = json["gauges"].keys.first.split(".").first.length + 1

  data = {
      "gauges" => json["gauges"].reduce({}){ |o, (k, v)| o[k[id_size..-1]] = v["value"]; o },
      "timers" => json["timers"].values.first
  }

  stats = Stats.new()

  metrics = ["heap_usage", "last_delay", "waiting_batches", "failed_stages", "throughput_timers_p75", "throughput_timers_p95", "throughput_timers_p98"]
  output = metrics.reduce({}){|acc, type| acc[type] = stats.send(type, data); acc}

rescue
  output = {}
end

puts output.to_json


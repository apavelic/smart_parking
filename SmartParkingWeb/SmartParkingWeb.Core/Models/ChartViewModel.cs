using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartParkingWeb.Core.Models
{
    public class ChartViewModel
    {
        public Dictionary<int, Dictionary<double, int>> MultipleHighchartData { get; set; }
        public Dictionary<double, double> HistoricalHighchartData { get; set; }
        public Dictionary<string, double> HistogramHighchartData { get; set; }

        public ChartViewModel()
        {
            MultipleHighchartData = new Dictionary<int, Dictionary<double, int>>();
            HistoricalHighchartData = new Dictionary<double, double>();
            HistogramHighchartData = new Dictionary<string, double>();
        }
    }
}

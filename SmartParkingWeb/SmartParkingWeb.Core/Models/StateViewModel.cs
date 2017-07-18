using SmartParkingWeb.Core.Enumerations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartParkingWeb.Core.Models
{
    public class StateViewModel
    {
        public int ParkingId { get; set; }
        public ParkingStateEnum State { get; set; }
        public DateTime Date { get; set; }

    }
}
